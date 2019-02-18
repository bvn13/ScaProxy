package ru.bvn13.scaproxy.engine

import java.io.{InputStreamReader, OutputStreamWriter}
import java.net.Socket

case class SocketTunnel(sender: Socket, receiver: Socket) extends Runnable {

  private val senderStream: InputStreamReader = new InputStreamReader(sender.getInputStream)

  private val receiverStream: OutputStreamWriter = new OutputStreamWriter(receiver.getOutputStream)

  var initialBuffer: List[String] = List()

  override def run(): Unit = {



  }
}

