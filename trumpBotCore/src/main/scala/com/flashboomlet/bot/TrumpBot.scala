package com.flashboomlet.bot

import io.scalac.slack.MessageEventBus
import io.scalac.slack.bots.IncomingMessageListener
import io.scalac.slack.common.BaseMessage
import io.scalac.slack.common.OutboundMessage

/**
  * This is the heart and soul of the project. All incommint and outgoing traffic to chat
  * will occur in the receive method.
  *
  * @param bus Message event bus used for listening to specific events
  */
class TrumpBot(override val bus: MessageEventBus) extends IncomingMessageListener {

  def receive: Receive = {
    case bm@BaseMessage(text, channel, user, dateTime, edited) =>
      println(s"Received message $text") // Handle message
      publish(OutboundMessage(channel, s"Okay, okay. Calm down.")) // Send message
    case _ => ()
  }
}