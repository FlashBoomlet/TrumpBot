package com.flashboomlet.bot

import com.fasterxml.jackson.databind.ObjectMapper
import com.flashboomlet.preprocessing.ClassifiedInput
import com.flashboomlet.preprocessing.FastSentimentClassifier
import com.flashboomlet.preprocessing.NLPUtil
import com.flashboomlet.preprocessing.naivebayes.WrappedClassifier
import com.flashboomlet.selection.Conversation
import com.typesafe.scalalogging.LazyLogging
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import io.scalac.slack.MessageEventBus
import io.scalac.slack.bots.IncomingMessageListener
import io.scalac.slack.common.BaseMessage
import io.scalac.slack.common.OutboundMessage

/**
  * This is the heart and soul of the project. All incoming and outgoing traffic to chat
  * will occur in the receive method.
  *
  * @param bus Message event bus used for listening to specific events
  */
class TrumpBot(
              override val bus: MessageEventBus,
              implicit val classifierWrapper: WrappedClassifier,
              implicit val objectMapper: ObjectMapper,
              implicit val pipeline: StanfordCoreNLP)
    extends IncomingMessageListener with LazyLogging {

  val UserId: String = "U2CRTV145"

  val MinuteInMillies = 60000

  val convoSelector: Conversation = new Conversation()

  val conversationId = 1

  /**
    * When A message is received in slack chat this method is called.
    *
    * @return Receive actor action
    */
  def receive: Receive = {
    case bm@BaseMessage(text, channel, user, dateTime, edited) =>
      if (UserId != user) {
        val primaryTopic = classifierWrapper.classifier.classify(text)
        val classifiedInput = ClassifiedInput(
          sentiment = FastSentimentClassifier.getSentiment(text),
          primaryTopic = primaryTopic,
          allTopics = prependPrimaryTopicToAll(primaryTopic,
            NLPUtil.getAllTopics(classifierWrapper.topics, text)),
          nounsAndPronouns = NLPUtil.getNouns(text),
          wordCount = text.split(' ').length,
          message = text,
          messageId = Math.floor(dateTime.toDouble).toInt,
          conversationId = conversationId
        )
        logger.info(s"Classified input: \n {}", classifiedInput.toString)
        val response = convoSelector.GenerateResponse(classifiedInput)
        // 40 word per minute typing
        val responseDelay = MinuteInMillies * (response.split(' ').length / 40)
        Thread.sleep(responseDelay)
        // free to use classified input here
        // publish(OutboundMessage(channel, "message goes here")) // Send message
      }
    case _ => ()
  }

      /**
        * Private method for adding thre primary topic to the lsit of all topics only if
        * it is not included in the simple NLP search for topics.
        *
        * @param primary Primary topic classification for a given text
        * @param all All plaintext topics found in a text
        * @return List of all topics, including only once instance of the primary topic.
        */
  private[this] def prependPrimaryTopicToAll(primary: String, all: List[String]): List[String] = {
    if (!all.contains(primary)) {
      primary +: all
    } else {
      all
    }
  }
}