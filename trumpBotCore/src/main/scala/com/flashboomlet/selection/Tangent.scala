package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState

/**
  * Created by ttlynch on 9/18/16.
  */
class Tangent {

  /**
    * Tangent will take the conversation on a tangent closely related to the topic that
    * the user was previously talking about.
    *
    * @param cs the current conversation state of the conversation
    * @param topic the topic to go off on a tangent with
    * @return a response to give to the user
    */
  def tangent(cs: ConversationState, topic: String): String = {

    if(cs.topicResponseCount > 1)
    {
      // Update Conversation State
      // back to parent topic
      ""
    }
    else if(cs.topicResponseCount > 0)
    {
      // Follow up tangent with more content
      "'"
    }
    else
    {
      // Find a way to go off on a tangent with the topic given
      // Update Conversation State
      // Start Tangent
      ""
    }
  }
}
