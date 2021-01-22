package net.seichi915.seichi915codecore.listener

import net.seichi915.seichi915codecore.Seichi915CodeCore
import net.seichi915.seichi915codecore.code.Code
import net.seichi915.seichi915codecore.database.Database
import net.seichi915.seichi915codecore.util.Implicits._
import org.bukkit.{Bukkit, ChatColor}
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.{EventHandler, Listener}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class PlayerJoinListener extends Listener {
  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit =
    Database.getCode(event.getPlayer) onComplete {
      case Success(value) =>
        val newCode = Code.generateRandomCode
        Database.setCode(event.getPlayer, newCode) onComplete {
          case Success(_) =>
            if (value.nonEmpty)
              Bukkit.getScheduler
                .runTask(
                  Seichi915CodeCore.instance,
                  (() =>
                     event.getPlayer.kickPlayer(
                       List(
                         s"${ChatColor.AQUA}[${ChatColor.GREEN}seichi915Network${ChatColor.AQUA}]${ChatColor.RESET}",
                         s"次のコードを生成しました: ${ChatColor.YELLOW}${ChatColor.UNDERLINE}${newCode.toString}${ChatColor.RESET}",
                         "",
                         s"${ChatColor.GRAY}次のコードは削除されました: ${value.get.toString}${ChatColor.RESET}"
                       ).mkString("\n")
                     )): Runnable
                )
            else
              Bukkit.getScheduler
                .runTask(
                  Seichi915CodeCore.instance,
                  (() =>
                     event.getPlayer.kickPlayer(
                       List(
                         s"${ChatColor.AQUA}[${ChatColor.GREEN}seichi915Network${ChatColor.AQUA}]${ChatColor.RESET}",
                         s"次のコードを生成しました: ${ChatColor.YELLOW}${ChatColor.UNDERLINE}${newCode.toString}${ChatColor.RESET}",
                         "",
                         ""
                       ).mkString("\n")
                     )): Runnable
                )
          case Failure(exception) =>
            exception.printStackTrace()
            Bukkit.getScheduler
              .runTask(Seichi915CodeCore.instance,
                       (() =>
                          event.getPlayer.kickPlayer(
                            "コードの生成に失敗しました。".toErrorMessage)): Runnable)
        }
      case Failure(exception) =>
        exception.printStackTrace()
        Bukkit.getScheduler
          .runTask(Seichi915CodeCore.instance,
                   (() =>
                      event.getPlayer.kickPlayer(
                        "コードの生成に失敗しました。".toErrorMessage)): Runnable)
    }
}
