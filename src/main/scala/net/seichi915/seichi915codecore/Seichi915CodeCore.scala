package net.seichi915.seichi915codecore

import net.seichi915.seichi915codecore.configuration.Configuration
import net.seichi915.seichi915codecore.listener._
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object Seichi915CodeCore {
  var instance: Seichi915CodeCore = _
}

class Seichi915CodeCore extends JavaPlugin {
  Seichi915CodeCore.instance = this

  override def onEnable(): Unit = {
    Configuration.saveDefaultConfig()
    Seq(
      new PlayerJoinListener
    ).foreach(Bukkit.getPluginManager.registerEvents(_, this))

    getLogger.info("seichi915CodeCoreが有効になりました。")
  }

  override def onDisable(): Unit = {
    getLogger.info("seichi915CodeCoreが無効になりました。")
  }
}
