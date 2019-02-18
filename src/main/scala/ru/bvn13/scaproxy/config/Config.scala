package ru.bvn13.scaproxy.config

import org.ekrich.config.{Config, ConfigFactory}

object Config {

  private val config: Config = ConfigFactory.systemEnvironment()
    .withFallback(ConfigFactory.parseResources("application.conf"))

  def host:String = config.getString("scaproxy.host")

  def port:Int = config.getInt("scaproxy.port")

}
