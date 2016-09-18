package com.flashboomlet.data

/**
  * Response is a model for the object that will be held in the response collection.
  * The response collection is a collection of responses given topics.
  *
  * @param content the actual content response
  * @param topics the topics that relate to the topic, sub topics found in the content
  * @param topic the topic that the naive bayes model found for the content
  * @param conversationState the state of the conversation (Start, Middle, and or End)
  * @param transitional a flag for if it is able to be used to transition the conversation
  * @param partOfTopic the part of the topic that it can go. (Start, Middle, and or End)
  * @param positiveSentiment a flag for if given a positive sentiment, this will work
  * @param negativeSentiment a flag for if given a negative sentiment, this will work
  * @param tangentTopic the topic to go tangent on
  */
case class Response (
  content: String,
  topics: Array[String],
  topic: String,
  conversationState: Int,
  transitional: Int,
  partOfTopic: Array[String],
  positiveSentiment: Int,
  negativeSentiment: Int,
  tangentTopic: String
)
