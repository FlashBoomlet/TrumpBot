package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState

/**
  * Created by ttlynch on 9/17/16.
  */
class EscapeMode {

  /**
    * Escape mode will allow the bot to escape the conversation and end it
    *
    * Since Donald Trump will always end up dictating the conversation, if he is unable to after
    * trouble mode has been activated, escape mode will be activated.
    *
    * Relative responses will be produced.
    *   Given many input, this will produce the same output.
    *
    * @param cs the current conversation state
    * @return a canned response to leave the conversation
    */
  def escapeMode(cs: ConversationState): String = {

    if(cs.topicResponseCount == 1){
      // Make them feel like trash
      // Could select from a list of them
      val response: String = "Since you don't seem to care about any real issues, you might " +
      "as well go get out of The United States. I am here trying to talk about the real topics " +
      "and all you want to do is dick around. Find yourself a bimbo and dick around with them." +
      " Seemed to work for Bill."
      response
    }
    else if(cs.topicResponseCount == 2){
      // canned response to completely end the conversation:
      val response: String = "Like I said, go find yourself a bimbo. I am done wasting my time " +
      "with people like you. And let me tell you, it is people like you, ruining America!!!! " +
      " I am trying to Make America Great Again and it is people like you that are ruining her " +
      " I am done with this conversation."
      response
    }
    else
    {
      "I am done with this conversation."
    }
  }

  /**
    * Recovered determines if the user has recovered or not from escape mode.
    *
    * @param cs the current conversation state
    * @param pcs the past conversation state
    * @return if the user has recovered from being banned
    */
  def recovered(cs: ConversationState, pcs: ConversationState): Boolean = {
    false
  }
}
