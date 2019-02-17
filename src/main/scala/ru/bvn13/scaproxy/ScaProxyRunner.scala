package ru.bvn13.scaproxy

import ru.bvn13.scaproxy.engine.ProxyServer

object ScaProxyRunner {

  private val proxyServer: ProxyServer = new ProxyServer

  def main(args: Array[String]): Unit = {

    proxyServer.start

  }

}
