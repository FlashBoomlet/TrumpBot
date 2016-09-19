package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState

/**
  * Created by ttlynch on 9/17/16.
  */
class Conversation {

  /**
    * Generate Response is a request from the user for a response.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to be sent to the user
    */
  def GenerateResponse(cs: ConversationState): String = {

    if(cs.conversationState < 1){
      ConversationEnd(cs)
    }
    else if(cs.conversationState == 1){
      // if the total amount of conversational topics of this conversation exceeds 5, leave exit mode.
      Conversation(cs)
    }
    else{
      ConversationStart(cs)
    }
  }

  /**
    * Conversation Start is the starting stage of the conversation. In here, Trump attempts to
    * gain control of the conversation.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to the user
    */
  private def ConversationStart(cs: ConversationState): String = {
    if(cs.topicResponseCount == 1){
      "Hello. How are you doing?"
    }
    else if(cs.topicResponseCount > 1){
      // update conversation state to get out of the start mode.
      if(cs.sentiment < 0){
        val response: String = "I am sorry to hear that. A vote for me, will help to change " +
        "that. I am Making America Great Again! \n"
        response + randomConversationStarter()
      }
      else
      {
        val response: String = "I am glad to hear that. I am Making America Great Again! \n"
        response + randomConversationStarter()
      }
    }
    ""
  }

  /**
    * The conversation is the bulk of the content. It is where the magic happens.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to the user
    */
  private def Conversation(cs: ConversationState): String = {
    if(cs.transitionState != 0)
    {
      randomConversationStarter()
    }
    else {
      // Select the response from the collection of responses that most closely matches a response
      // from the user.
      if(cs.sentiment < 0){
        // generate response. If greater than 2 replies and still negative. Escape mode
      }
      else{
        // If greater than 3 replies, then transition to a new topic.
      }
      ""
    }
  }

  /**
    * Conversation end is the ending of a conversation. It can be triggered by multiple topics
    * being covered or by an escape mode.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to send to the user
    */
  private def ConversationEnd(cs: ConversationState): String = {
    // Find average sentiment of the conversation by the other person to gage interest.

    if(cs.sentiment < 0)
    {
      // escape mode
      "'"
    }
    else if(cs.sentiment > 0)
    {
      //    thank for support, ask for donation, tell them to preach the good word.
      ""
    }
    else
    {
      // leave in a civil manner. Refer them to the website to find out more details.
      val response: String = "I was a pleasure talking to you. I have an event I must attend so " +
      "I am signing off now. If you have any more questions please drop by my website. I have " +
      "all of my policies outlined. I am sure that you will find the Answers to all of your " +
      "questions there. You will surely see why a vote for my is far better than anyone else! " +
      " Make America Great Again! - Donald J. Trump"
      response
    }
  }

  /**
    * Random Conversation Starter will select a random response with a lot of topics that are the
    * the same to respond with. The response is guaranteed to be unique and not used along with on
    * a topic that has not been used yet.
    *
    * @return a random response from the database that has not be used prior.
    */
  private def randomConversationStarter(): String = {
    // Select a random response with a lot of topics to start out with. Make this random so that
    // way not all of the conversations will start out the same way.
    ""
  }
}
