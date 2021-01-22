package net.seichi915.seichi915codecore.configuration

import net.seichi915.seichi915codecore.Seichi915CodeCore

object Configuration {
  def saveDefaultConfig(): Unit = Seichi915CodeCore.instance.saveDefaultConfig()

  def getDatabaseHost: String =
    Seichi915CodeCore.instance.getConfig.getString("Database.Host")

  def getDatabaseUsername: String =
    Seichi915CodeCore.instance.getConfig.getString("Database.Username")

  def getDatabasePassword: String =
    Seichi915CodeCore.instance.getConfig.getString("Database.Password")

  def getDatabasePort: Int =
    Seichi915CodeCore.instance.getConfig.getInt("Database.Port")

  def getDatabaseName: String =
    Seichi915CodeCore.instance.getConfig.getString("Database.DatabaseName")
}
