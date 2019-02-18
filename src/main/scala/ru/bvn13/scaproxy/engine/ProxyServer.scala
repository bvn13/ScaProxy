package ru.bvn13.scaproxy.engine

import java.io.IOException
import java.net.{InetAddress, ServerSocket, Socket}
import java.util.concurrent.{ExecutorService, Executors}

import ru.bvn13.scaproxy.config.Config

class ProxyServer {

  private val executorService: ExecutorService = Executors.newCachedThreadPool

  private var clients: List[ClientListener] = List()

  def start():Unit = {
    println("ScaProxy started")

    println("listening "+Config.host+":"+Config.port)

    executorService.execute(mainThread)

  }

  def mainThread:Runnable = () => {

    val serverSocket: ServerSocket = new ServerSocket(Config.port, 0, InetAddress.getByName(Config.host))

    try {

      while (!serverSocket.isClosed) {
        val socket: Socket = serverSocket.accept()
        val clientListener: ClientListener = ClientListener(socket)
        executorService.execute(clientListener)
        clients = clientListener :: clients
      }

    } catch {
      case e: IOException => e.printStackTrace()
    } finally {
      serverSocket.close()
    }

  }

}
