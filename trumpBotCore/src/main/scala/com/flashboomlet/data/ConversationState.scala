package com.flashboomlet.data

/**
  * Conversation State is a state tracking object to track where the conversation is at
  *
  * @param conversationId the id of the conversation
  * @param emotionalState the emotional state of the conversation
  * @param lengthState the length the message within the conversation
  * @param sentiment the sentiment of the conversation
  * @param topic the topic of the conversation
  * @param conversationState the conversation state of whether is is a question or statement
  * @param transitionState a flag for if the conversation is in a transition state
  * @param topicResponseCount a count for how many messages have been sent on the current topic
  * @param troubleMode a flag for if the conversation is in trouble
  * @param escapeMode a flag for if the escape mode needs to be activated
  * @param tangent a flag for if the conversation has gone on a tangent from the original state
  * @param parentTopic a topic from which the tangent started
  */
case class ConversationState (
  conversationId: Long,
  emotionalState: String,
  lengthState: Int,
  sentiment: Long,
  topic: String,
  conversationState: String,
  transitionState: Int,
  topicResponseCount: Int,
  troubleMode: Int,
  escapeMode: Int,
  tangent: Int,
  parentTopic: String
)
