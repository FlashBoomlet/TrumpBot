package com.flashboomlet.data

/**
  * Conversation State is a state tracking object to track where the conversation is at
  *
  * @param conversationId the id of the conversation (this id should never change and should stay the same for that conversation)
  * @param messageId the id of the message that corresponds to the current state
  * @param lengthState the length the message within the conversation (the number of calculaterd words)
  * @param sentiment the sentiment of the conversation
  * @param topic the topic of the conversation
  * @param topics the topics/nouns of the conversation
  * @param conversationState the conversation state of whether is is a question or statement (2, Start, 1 Conversation, 0 end)
  * @param transitionState a flag for if the conversation is in a transition state
  * @param topicResponseCount a count for how many messages have been sent on the current topic
  * @param troubleMode a flag for if the conversation is in trouble
  * @param escapeMode a flag for if the escape mode needs to be activated
  * @param tangent a flag for if the conversation has gone on a tangent from the original state
  * @param parentTopic a topic from which the tangent started
  * @param message the actual message content
  * @param responseMessage the message being send back
  * @param tangentCount the count of tangents that have occurred
  */
case class ConversationState(
  conversationId: Long,
  messageId: Int,
  lengthState: Int,
  sentiment: Long,
  topic: String,
  topics: List[String],
  conversationState: Int,
  transitionState: Boolean,
  topicResponseCount: Int,
  troubleMode: Boolean,
  escapeMode: Boolean,
  tangent: Boolean,
  parentTopic: String,
  message: String,
  responseMessage: String,
  tangentCount: Int
)
