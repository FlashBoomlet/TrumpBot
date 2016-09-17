package com.flashboomlet

import akka.actor.ActorSystem
import akka.actor.Props
import bot.TrumpBotBundle
import io.scalac.slack.MessageEventBus
import io.scalac.slack.api.BotInfo
import io.scalac.slack.api.Start
import io.scalac.slack.common.Shutdownable
import io.scalac.slack.common.actors.SlackBotActor
import io.scalac.slack.websockets.WebSocket

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Driver for the TrumpBot. Contains entry point
  */
object Driver extends Shutdownable {

  val System = ActorSystem("trump")
  val EventBus = new MessageEventBus
  val SlackBot = System.actorOf(
    Props(classOf[SlackBotActor], new TrumpBotBundle(), EventBus, this, None), "slack-com.flashboomlet.bot")
  var BotInfo: Option[BotInfo] = None

  /** Entry point to the TrumpBot program
    *
    * @param args command-line arguments
    */
  def main(args: Array[String]) {
    try {
      SlackBot ! Start

      Await.result(System.whenTerminated, Duration.Inf)
      println("Shutdown successful...")
    } catch {
      case e: Exception =>
        println("An unhandled exception occurred...", e)
        System.terminate()
        Await.result(System.whenTerminated, Duration.Inf)
    }
  }

  sys.addShutdownHook(shutdown())

  /** Will probably never be used but it is here just in case hillary gets elected */
  override def shutdown(): Unit = {
    SlackBot ! WebSocket.Release
    System.terminate()
    Await.result(System.whenTerminated, Duration.Inf)
  }
}
