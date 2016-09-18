package com.flashboomlet.data

/**
  * Created by ttlynch on 9/17/16.
  */
case class SlackMessage (
  conversationId: Int,
  messageId: Int,
  channel: String,
  text: String,
  user: String,
  time: Long,
  response: String
)
