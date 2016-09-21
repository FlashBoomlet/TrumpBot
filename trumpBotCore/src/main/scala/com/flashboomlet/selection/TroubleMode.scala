package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState

/**
  * Created by ttlynch on 9/17/16.
  */
class TroubleMode {

  val r = scala.util.Random

  /**
    * Trouble Mode will take the given input and get the bot out of the situation
    * and back on track into a topic that it knows.
    *
    * This function will produce a stochastic output. It will randomly suggest a topic
    * to get back on track with.
    *
    * @param cs the conversation state of the conversation
    * @return the response
    */
  def troubleMode(cs: ConversationState): String = {

    // Pick a random topic that hasn't been covered and can a response
    "Are you even taking this conversation seriously? There are important issues to worry about " +
    "like " + selectRandomTopic() + "."
  }

  /**
    * Select Random Topic Randomly selects a topic or sub topic from the database of
    * known responses.
    *
    * @return a random topic from the response database
    */
  def selectRandomTopic(): String = {
    // TODO: Update this list when the list changes
    val topics = List(
      "wall",
      "terrorism" ,
      "china" ,
      "taxes" ,
      "children" ,
      "immigration" ,
      "trade" ,
      "police" ,
      "guns" ,
      "education" ,
      "jobs" ,
      "myself",
      "Crooked Hillary",
      "abortion",
      "health care",
      "foreign policy")

    val len = topics.length
    val i = r.nextInt(len)
    topics(i)
  }

}
