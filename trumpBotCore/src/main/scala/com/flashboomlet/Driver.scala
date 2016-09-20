package com.flashboomlet

import akka.actor.ActorSystem
import akka.actor.Props
import bot.TrumpBotBundle
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.flashboomlet.bot.SlackConfig
import com.flashboomlet.preprocessing.NLPClientFactory
import com.flashboomlet.preprocessing.naivebayes.NaiveBayesClassifierFactory
import com.flashboomlet.preprocessing.naivebayes.WrappedClassifier
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
  implicit val objectMapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)
    .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
  val BotInfo: Option[BotInfo] = None
  val DefaultNLPClient = NLPClientFactory.defaultNounParser()
  val Classifier: WrappedClassifier = NaiveBayesClassifierFactory.loadClassifier()

  /** Entry point to the TrumpBot program
    *
    * @param args command-line arguments
    */
  def main(args: Array[String]) {
    println(s"With API key ${SlackConfig.ApiKey}")
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
