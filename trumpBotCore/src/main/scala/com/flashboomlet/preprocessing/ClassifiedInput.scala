package com.flashboomlet.preprocessing

/**
  * Case class for wrapping the preprocessed message input for the bot
  * @param sentiment The sentiment of the message
  * @param primaryTopic The primary topic classified using the naive bayes theorem
  * @param allTopics All topics included in text including primary classification
  * @param nounsAndPronouns All nouns and pronouns in the sentence
  * @param message The entire original message.
  */
case class ClassifiedInput(
  sentiment: Sentiment,
  primaryTopic: String,
  allTopics: List[String],
  nounsAndPronouns: List[String],
  wordCount: Int,
  message: String
)
