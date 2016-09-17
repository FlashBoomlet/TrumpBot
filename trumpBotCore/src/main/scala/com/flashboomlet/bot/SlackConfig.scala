package com.flashboomlet.bot

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

/**
  * Configuration object for custom settings
  */
object SlackConfig {
  private[this] val configuration: Config = ConfigFactory.load("application.conf")

  val ApiKey: String = configuration.getString("api.key")

  val Channel: String = configuration.getString("channel")

}
