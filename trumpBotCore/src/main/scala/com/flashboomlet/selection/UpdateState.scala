package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.preprocessing.ClassifiedInput
import com.flashboomlet.preprocessing.Sentiment

/**
  * Created by ttlynch on 9/17/16.
  *
  * Update state is in charge of updating the state of the conversation so that responses may be
  * selected
  */
class UpdateState {

  val db: MongoDatabaseDriver = new MongoDatabaseDriver()
  val maxTopics = 7
  val trcMax = 5

  /**
    * This function will update the state of the conversation to keep track of where
    * the conversation is and to more accurately respond to the input
    *
    * @param ci the classified input from the pre processing
    * @return a conversation State of the conversation state
    */
  def updateState(ci: ClassifiedInput): ConversationState = {
    val pastStates = db.getConversationStates(ci.conversationId)
    pastStates.foreach(s => println(s))
    if (pastStates.length < 1) {
      initializeState(ci)
    } else {
      val lastState = pastStates.sortBy(_.messageId).reverse.head // TODO: check to see if it is max id first
      val topicCount = pastStates.map(_.topic).distinct.length
      val trc = getTRC(lastState)
      val transition = trc > trcMax
      val tm = troubleMode(lastState, ci, pastStates)
      val em = escapeMode(pastStates, lastState, tm)
      val topic = getTopic(lastState, ci)
      val parentTopic = if (lastState.parentTopic == topic) { "" } else { lastState.parentTopic }
      val tangent = lastState.parentTopic != "" && lastState.tangent
      val state = getState(lastState, pastStates, topicCount, tm, em)

      ConversationState(
        conversationId = ci.conversationId,
        messageId = ci.messageId,
        lengthState = ci.wordCount,
        sentimentConfidence = scala.math.ceil(ci.sentiment.confidence.toDouble).toLong,
        sentimentClass = ci.sentiment.result,
        topic = topic,
        topics = ci.allTopics,
        conversationState = state,
        transitionState = transition,
        topicResponseCount = trc,
        troubleMode = tm,
        escapeMode = em,
        tangent = tangent,
        parentTopic = parentTopic,
        message = ci.message,
        responseMessage = "",
        tangentCount = lastState.tangentCount
      )
    }
  }

  /**
    * Initialize State creates a conversation state if no state has been created yet
    *
    * @param ci the classified input from the pre processing
    * @return a conversation state for the current conversation state
    */
  private def initializeState(ci: ClassifiedInput): ConversationState = {
    ConversationState(
      conversationId = ci.conversationId,
      messageId = ci.messageId,
      lengthState = ci.wordCount,
      sentimentConfidence = scala.math.ceil(ci.sentiment.confidence.toDouble).toLong,
      sentimentClass = ci.sentiment.result,
      topic = ci.primaryTopic,
      topics = ci.allTopics,
      conversationState = 2,
      transitionState = false,
      topicResponseCount = 0,
      troubleMode = false,
      escapeMode = false,
      tangent = false,
      parentTopic = "",
      message = ci.message,
      responseMessage = "",
      tangentCount = 0
    )
  }

  /**
    * Get TRC gets the topic response count
    *
    * @param lastState the last known state of the conversation
    * @return the topic response count
    */
  private def getTRC(lastState: ConversationState): Int = {
    if (lastState.tangent || lastState.transitionState) {
      0
    } else {
      lastState.topicResponseCount + 1
    }
  }

  /**
    * Get topic will determine if the tangent has gone on for too long if applicable, thus
    * if it has, it will switch it back. If if has not gone on a tangent, then the topic
    * will match that of the pre processing input
    *
    * @param lastState the last known state of the conversation
    * @param ci the classified input from the pre processing
    * @return the topic for this current state
    */
  private def getTopic(lastState: ConversationState, ci: ClassifiedInput): String = {
    if(lastState.tangent && lastState.topicResponseCount > 2){
      lastState.parentTopic
    }
    else{
      ci.primaryTopic
    }
  }

  /**
    * The get state returns what state the conversation is now in.
    *
    * The state of the conversation will be as follows:
    *   2: Start
    *   1: Middle
    *   0: End
    *
    * @param lastState the last known state of the conversation
    * @param pastStates the past states of the conversation
    * @param topicCount the count of topics that have been covered thus far
    * @param tm a flag for if the state is entering trouble mode
    * @param em a flag for if the state is entering escape mode
    * @return the state of the conversation
    */
  private def getState(
      lastState: ConversationState,
      pastStates: List[ConversationState],
      topicCount: Int,
      tm: Boolean,
      em: Boolean): Int = {

    if (lastState.conversationState ==  2) {
      // If the conversation has already responded with two messages, then it is ready to be moved on.
      if (pastStates.sortBy(_.messageId).length == 2) {
        1
      } else {
        2
      }
    } else if (lastState.conversationState == 1) {
      if (topicCount >= maxTopics && lastState.topicResponseCount >= trcMax) {
        1 // Move on to the end
      } else if (em) {
        0 // Stay in conversation state
      } else {
        1
      }
    } else {
      0
    }
  }

  /**
    * Trouble mode determines if the conversation is entering trouble mode.
    *
    * The flags are as follows:
    *   1: Enter
    *   0: You're fine
    *
    * @param lastState the last known state of the conversation
    * @param ci the classified input from the pre processor
    * @param pastStates the past states of the conversation
    * @return a flag for if the conversation is entering a trouble mode.
    */
  private def troubleMode(
    lastState: ConversationState,
    ci: ClassifiedInput,
    pastStates: List[ConversationState]): Boolean ={

    // Find average sentiment of the conversation by the other person to gage interest.
    val lengths = pastStates.map(_.lengthState)
    val count = pastStates.length
    val avgLength = lengths.sum / count
    val standardDev = Math.sqrt(lengths.map(l => (l - avgLength) * (l - avgLength)).sum / count)

    if (lastState.troubleMode) {
      // already in trouble mode
      // check if they have gotten out or upgrade to escape
      ci.sentiment.result == "Negative" && math.ceil(ci.sentiment.confidence.toDouble).toLong > 90
    } else {
      // not already in trouble mode
      // check to see if they deserve to be in
      if (lastState.sentimentClass == "Negative" && lastState.sentimentConfidence > 90) {
        // And the current state is negative, bam
        // The user must be out of the start to be in trouble mode
        if (count > 2) {
          lastState.lengthState > (avgLength + standardDev)
        } else {
          false
        }
      } else {
        false
      }
    }
  }

  /**
    * Escape mode determines if the conversation is entering escape mode.
    *
    * To enter, the program must have entered trouble mode twice and or there must be back to back
    * messages with trouble mode
    *
    * The flags are as follows:
    *   1: Enter
    *   0: You're fine
    *
    * @param pastStates the past states of the conversation
    * @param lastState the last known state of the conversation
    * @param trouble if the conversation is entering trouble mode
    * @return
    */
  private def escapeMode(
      pastStates: List[ConversationState],
      lastState: ConversationState,
      trouble: Boolean): Boolean = {
    // if the the past message was in trouble and this one is in trouble, we in trouble
    val tmCount = pastStates.count(_.troubleMode)
    tmCount >= 2 && trouble
  }

  /**
    * Sent to long converts the sentiment to a long
    *
    * The output will be as such:
    *   -1: Negative
    *   0: Neutral
    *   1: Positive
    *
    * @param s the sentiment of the pre processing
    * @return a long
    */
  private def sentToLong(s: Sentiment): Long = {
    if (s.result == "Positive") {
      1L
    } else if (s.result == "Neutral") {
      0L
    } else {
      -1L
    }
  }
}
