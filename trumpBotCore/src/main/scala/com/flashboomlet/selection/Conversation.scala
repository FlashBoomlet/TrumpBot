package com.flashboomlet.selection

import com.flashboomlet.data.ConversationState
import com.flashboomlet.data.Response
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.preprocessing.ClassifiedInput
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by ttlynch on 9/17/16.
  *
  * Conversation will select responses from the database and output a response
  */
class Conversation extends LazyLogging {

  val db: MongoDatabaseDriver = new MongoDatabaseDriver()
  val stateMachine: UpdateState = new UpdateState()
  val em: EscapeMode = new EscapeMode()
  val tm: TroubleMode = new TroubleMode()

  /**
    * Generate Response is a request from the user for a response.
    *
    * @param ci the current conversation state of the conversation
    * @return a response to be sent to the user
    */
  def GenerateResponse(ci: ClassifiedInput): String = {

    val id = ci.conversationId // Assuming the conversation id will always be one.
    val pastConversations = db.getConversationStates(id)
    // Current State of the Conversation
    val cs = stateMachine.updateState(ci)
    logger.info(s"Conversation State: \n {}", cs.toString)

    val response = if(cs.conversationState == 0){
      // If the conversation State is triggered to be over then
      ConversationEnd(cs, pastConversations)
    }
    else if(cs.conversationState == 1){
      // if the total amount of conversational topics of this conversation exceeds 5, leave exit mode.
      Conversation(cs, pastConversations)
    }
    else{
      ConversationStart(cs)
    }

    // Update Conversation State
    val finalCS = updateResponseMsg(cs, response)
    // Insert Conversation State into DB
    db.insertConversationState(finalCS)
    // Return Response
    response
  }

  /**
    * Conversation Start is the starting stage of the conversation. In here, Trump attempts to
    * gain control of the conversation.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to the user
    */
  private def ConversationStart(cs: ConversationState): String = {
    if(cs.topicResponseCount <= 1 ){
      "Hello, I am Donald J. Trump, the Republican Presidential Nominee. How are you doing?"
    }
    else{
      // update conversation state to get out of the start mode.
      if(cs.sentiment < 0){
        val response: String = "I am sorry to hear that. I am Making America Great Again! \n"
        response + randomConversationStarter()
      }
      else
      {
        val response: String = "I am glad to hear that. I am Making America Great Again! \n"
        response + randomConversationStarter()
      }
    }
  }

  /**
    * The conversation is the bulk of the content. It is where the magic happens.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to the user
    */
  private def Conversation(
    cs: ConversationState,
    pastStates: List[ConversationState]): String = {

    if(cs.transitionState != 0)
    {
      randomConversationStarter()
    }
    else {
      // Select the response from the collection of responses that most closely matches a response
      // from the user.
      if(cs.escapeMode == 1){ /* Order if these if's matters!!! */
        em.escapeMode(cs)
      }
      else if(cs.troubleMode == 1){
        tm.troubleMode(cs)
      }
      else{
        // If greater than 3 replies, then transition to a new topic.
        val transition: String = if(cs.transitionState == 1){
          "\nWhat other questions do you have for me? "
        }
        else{
          ""
        }
        getResponse(cs, pastStates) + transition
      }
    }
  }

  /**
    * Get Response gets a response to send to the end user. Let's be honest,
    * this part is pure magic. Nobody really knows what is going on.
    *
    * @param cs the current conversation state
    * @param pastStates the past states of the conversation state
    * @return
    */
  private def getResponse(
    cs: ConversationState,
    pastStates: List[ConversationState]): String = {

    val responses: List[Response] = db.getResponses()
    val topics = cs.topics
    // TODO: Insert code to get out if there is a canned trigger first
    val respFiltered = responses.filter(_.topics == cs.topic)
    val pastResponses = pastStates.map(_.responseMessage)

    val responsesWithSimilarity = respFiltered.map { r =>
      (
        r,
        percentSimilar(topics, r.topics.toList),
        pastResponses
      )
    }

    val positiveFlag = if(cs.sentiment < 0){0}else{1}

    val validResponses = responsesWithSimilarity
      .filter(s => !s._3.contains(s._1.content))
      .filter(s => s._1.positiveSentiment == positiveFlag )
      .sortBy(_._2).reverse

    if(validResponses.length < 1){
      // Well we messed up, time to leave
      ConversationEnd(cs, pastStates)
    }
    else{
      validResponses.head._1.content
    }
    // TODO: Add logic to go on tangent and update tangent count
  }

  /**
    * Determines how similar the data is
    *
    * @param topics the topics of the current at question state
    * @param responseTopics the topics of a possible response
    * @return the percent of similarity between the two
    */
  private def percentSimilar(topics: List[String], responseTopics: List[String]): Double = {
    val len: Double = topics.length
    responseTopics.map( r => (r, topics)).count( obj => obj._2.contains(obj._1))/len
  }

  /**
    * Conversation end is the ending of a conversation. It can be triggered by multiple topics
    * being covered or by an escape mode.
    *
    * @param cs the current conversation state of the conversation
    * @return a response to send to the user
    */
  private def ConversationEnd(
    cs: ConversationState,
    pastStates: List[ConversationState]): String = {

    // Find average sentiment of the conversation by the other person to gage interest.
    val lengths = pastStates.map(_.sentiment)
    val count = pastStates.length
    val avg = if(count != 0) {
      lengths.sum / count
    }
    else{
      0
    }

    val sentThreshold = 0.5

    if(cs.sentiment < sentThreshold)
    {
      // escape mode
      em.escapeMode(cs)
    }
    else if(avg > sentThreshold)
    {
      //    thank for support, ask for donation, tell them to preach the good word.
      val response: String = "Thank you for your support! I greatly appreciate it. Together we " +
      "will will make America Great Again!\n\n If you would like to help out with my campaign, " +
      "the easiest way to get involved would be to go to https://www.donaldjtrump.com/ and " +
      "make a donation. \n\n Together we will beat corrupt Hillary!"
      response
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
    "\nWhat questions do you have for me? "
  }

  /**
    * Update Tangent Count will update the conversation state if it is going into a tangent
    *
    * @param cs the current conversation state
    * @param topic the topic that will be the new tangent
    * @return an updated conversation state
    */
  private def updateTangentCount(cs: ConversationState, topic: String): ConversationState = {
    ConversationState(
      conversationId = cs.conversationId,
      messageId = cs.messageId,
      lengthState = cs.lengthState,
      sentiment = cs.sentiment,
      topic = topic, // Change the topic
      topics = cs.topics,
      conversationState = cs.conversationState,
      transitionState = cs.transitionState,
      topicResponseCount = cs.topicResponseCount,
      troubleMode = cs.troubleMode,
      escapeMode = cs.escapeMode,
      tangent = 1, // set to 1
      parentTopic = cs.topic, // Change the topic
      message = cs.message,
      responseMessage = cs.responseMessage,
      tangentCount = cs.tangentCount + 1 // Increment the tangent count
    )
  }

  /**
    * Update Response Message will update the conversation state with the response message
    *
    * @param cs the current conversation state
    * @param msg the msg that will be the response
    * @return an updated conversation state
    */
  private def updateResponseMsg(cs: ConversationState, msg: String): ConversationState = {
    ConversationState(
      conversationId = cs.conversationId,
      messageId = cs.messageId,
      lengthState = cs.lengthState,
      sentiment = cs.sentiment,
      topic = cs.topic,
      topics = cs.topics,
      conversationState = cs.conversationState,
      transitionState = cs.transitionState,
      topicResponseCount = cs.topicResponseCount,
      troubleMode = cs.troubleMode,
      escapeMode = cs.escapeMode,
      tangent = cs.tangent,
      parentTopic = cs.parentTopic,
      message = cs.message,
      responseMessage = msg,
      tangentCount = cs.tangentCount
    )
  }
}
