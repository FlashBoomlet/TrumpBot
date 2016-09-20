package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState

/**
  * Created by ttlynch on 9/17/16.
  */
class UpdateState {

  /*
   * This function will update the state of the conversation to keep track of where
   * the conversation is and to more accurately respond to the input
   */
  def updateState(): ConversationState = {

    // if sub topics cannot be found try trouble mode.
    // if sub topics cannot be found and trouble mode fails, escape mode.
    // if the user is negative and not following in the conversation, escape mode. \

    ConversationState(conversationId = 0,
      emotionalState = "",
      lengthState = 0,
      sentiment = 0,
      topic = "",
      topics = List(),
      conversationState = 0,
      transitionState = 0,
      topicResponseCount = 0,
      troubleMode = 0,
      escapeMode = 0,
      tangent = 0,
      parentTopic = ""
    )
  }

}
