package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState

/**
  * Created by ttlynch on 9/17/16.
  */
class TroubleMode {

  /**
    * Trouble Mode will take the given input and get the bot out of the situation
    * and back on track into a topic that it knows.
    *
    * This function will produce a stochastic output. It will randomly suggest a topic
    * to get back on track with.
    *
    * @param cs the conversation state of the converstion
    * @return the response
    */
  def troubleMode(cs: ConversationState): String = {

    // Pick a random topic that hasn't been covered and can a response
    "return"
  }

  /**
    * Select Random Topic Randomly selects a topic or sub topic from the database of
    * known responses.
    *
    * @return a random topic from the response database
    */
  def selectRandomTopic(): String = {
    "clinton"
  }

  /**
    * Recovered determines if the user has recovered or not from trouble.
    *
    * @param cs the current conversation state
    * @param pcs the past conversation state
    * @return if the user deserves to still keep the conversation going
    */
  def recovered(cs: ConversationState, pcs: ConversationState): Boolean = {
    false
  }
}
