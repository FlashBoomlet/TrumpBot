package com.flashboomlet.bot

import akka.actor.ActorContext
import akka.actor.ActorRef
import akka.actor.Props
import com.flashboomlet.Driver
import io.scalac.slack.BotModules

/**
  * Bundle of patriotism!
  */
class TrumpBotBundle extends BotModules {

  /**
    * Needed so multiple bots may be deployed and inherited. This is necessary to interface with
    * the API even though there is currently only one bot.
    *
    * @param context Actor context to initialize bots in
    * @param websocketClient websocket client to listen with
    */
  override def registerModules(context: ActorContext, websocketClient: ActorRef): Unit = {
    context.actorOf(
      Props(
        classOf[TrumpBot],
        Driver.EventBus,
        Driver.Classifier,
        Driver.objectMapper,
        Driver.DefaultNLPClient),
      "trumpBot")
  }
}
