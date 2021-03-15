package net.seichi915.seichi915codecore.database

import net.seichi915.seichi915codecore.Seichi915CodeCore
import net.seichi915.seichi915codecore.code.Code
import net.seichi915.seichi915codecore.configuration.Configuration
import org.bukkit.entity.Player
import scalikejdbc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Database {
  Class.forName("com.mysql.jdbc.Driver")

  private val dbName =
    Seichi915CodeCore.instance.getDescription.getName.toLowerCase

  ConnectionPool.add(
    dbName,
    s"jdbc:mysql://${Configuration.getDatabaseHost}:${Configuration.getDatabasePort}/${Configuration.getDatabaseName}",
    Configuration.getDatabaseUsername,
    Configuration.getDatabasePassword
  )

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled = false
  )

  def getCode(player: Player): Future[Option[Code]] = Future {
    NamedDB(dbName) localTx { implicit session =>
      sql"SELECT code FROM codes WHERE uuid = ${player.getUniqueId.toString}"
        .map { resultSet =>
          val code = resultSet.string("code")
          val charArray = code.toCharArray
          val first =
            s"${charArray(0)}${charArray(1)}${charArray(2)}${charArray(3)}"
          val middle =
            s"${charArray(4)}${charArray(5)}${charArray(6)}${charArray(7)}".toInt
          val last =
            s"${charArray(8)}${charArray(9)}${charArray(10)}${charArray(11)}"
          Code(first, middle, last)
        }
        .list()
        .apply()
        .headOption
    }
  }

  def setCode(player: Player, code: Code): Future[Unit] = Future {
    getCode(player) onComplete {
      case Success(value) =>
        if (value.nonEmpty)
          NamedDB(dbName) localTx { implicit session =>
            sql"UPDATE codes SET code = ${code.toString} WHERE uuid = ${player.getUniqueId.toString}"
              .update()
              .apply()
          } else
          NamedDB(dbName) localTx { implicit session =>
            sql"INSERT INTO codes (uuid, code) VALUES (${player.getUniqueId.toString}, ${code.toString})"
              .update()
              .apply()
          }
      case Failure(exception) => throw exception
    }
  }
}
