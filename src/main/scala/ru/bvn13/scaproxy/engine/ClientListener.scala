package ru.bvn13.scaproxy.engine


import java.io.{BufferedReader, InputStreamReader}
import java.net.{InetAddress, Socket}
import java.util.concurrent.{ExecutorService, Executors}
import java.util.regex.{Matcher, Pattern}

case class ClientListener(clientSocket: Socket) extends Runnable {

  private val RE_HOST = Pattern.compile("^Host: (\\S)+:(\\d)*")

  private var buffer: List[String] = List()

  private var outgoingTunnel: SocketTunnel = ???
  private var incomingTunnel: SocketTunnel = ???

  private val executorService: ExecutorService = Executors.newFixedThreadPool(2)

  override def run(): Unit = {

    val clientStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream))
    var line = clientStream.readLine

    while (line != null && !line.isEmpty) {
      buffer = line :: buffer
    }

    if (buffer.length >= 2) {
      val matcher: Matcher = RE_HOST.matcher(buffer(2))
      if (matcher.matches) {
        val host:String = matcher.group(1)
        val port:String = if (matcher.groupCount > 2) matcher.group(2) else "80"

        val remoteSocket: Socket = new Socket(InetAddress.getByName(host), port.toInt)

        outgoingTunnel = SocketTunnel(sender = clientSocket, receiver = remoteSocket)
        outgoingTunnel.initialBuffer = buffer
        incomingTunnel = SocketTunnel(sender = remoteSocket, receiver = clientSocket)

        executorService.execute(outgoingTunnel)
        executorService.execute(incomingTunnel)
      }
    }

  }
}
