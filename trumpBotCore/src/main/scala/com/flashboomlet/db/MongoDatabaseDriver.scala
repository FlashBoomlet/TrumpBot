package com.flashboomlet.db

import com.flashboomlet.data.Response
import com.flashboomlet.data.SlackMessage
import com.typesafe.scalalogging.LazyLogging
import com.flashboomlet.data.ConversationState
import com.flashboomlet.db.implicits.MongoImplicits
import reactivemongo.api.BSONSerializationPack.Writer
import reactivemongo.api.DefaultDB
import reactivemongo.api.FailoverStrategy
import reactivemongo.api.MongoConnection
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success


/**
  * Database driver for MongoDB
  */
class MongoDatabaseDriver
  extends MongoConstants
  with LazyLogging
  with MongoImplicits {

  val driver = new MongoDriver()

  val connection = driver.connection(List(DatabaseIp))

  val db: DefaultDB = Await.result(connection.database(TrumpBotDatabaseString, FailoverStrategy.default), Duration.Inf)

  val conversationStateCollection: BSONCollection = db(ConversationStateCollection)

  val slackMessageCollection: BSONCollection = db(SlackMessageCollection)

  val responseCollection: BSONCollection = db(ResponseCollection)

    /**
    * Gets a conversation state given a conversation state id
    *
    * @param id the conversation id that corresponds to the current conversation.
    * @return the conversation state of the conversation
    */
  def getConversationStates(id: Long): List[ConversationState] ={
    val future: Future[List[ConversationState]] = conversationStateCollection
      .find(BSONDocument(
        ConversationStateConstants.ConversationId -> id ))
      .cursor[ConversationState]().collect[List]()

    Await.result(future, Duration.Inf)
  }

  /**
    * Gets the responses from the response collection
    *
    * @return the responses
    */
  def getResponses(): List[Response] ={
    Await.result(responseCollection.find(BSONDocument())
    .cursor[Response]().collect[List](), Duration.Inf)
  }

  /** Simply inserts a conversation state Model */
  def insertConversationState(cs: ConversationState): Unit = {
    insert(cs, conversationStateCollection)
  }

  /**
    * Updates a conversation state model
    *
    * @param cs a conversation state to find and update
    */
  def updateConversationState(cs: ConversationState): Unit = {
    val selector = BSONDocument(
      ConversationStateConstants.ConversationId -> cs.conversationId,
      ConversationStateConstants.MessageId -> cs.messageId)
    conversationStateCollection.findAndUpdate(selector, cs).onComplete {
      case Success(result) => logger.info("successfully updated conversation state")
      case _ => logger.error(s"failed to update the conversation state ${cs.conversationId}")
    }
  }

  /** Simply inserts a slack message Model */
  def insertSlackMessage(msg: SlackMessage): Unit = {
    insert(msg, slackMessageCollection)
  }

  /** Simply inserts a Response Model */
  def insertResponse(msg: Response): Unit = {
    insert(msg, responseCollection)
  }

  /**
    * Inserts an object with a defined implicit BSONWriter into the provided
    * collection and then returns the new BSONObjectID associated with the
    * newly inserted document.
    *
    * @param document Class of type T to be inserted as a [[BSONDocument]]
    * @param coll Collection to insert the [[BSONDocument]] into
    * @param writer Implicit writer associated with the Object of type T
    * @tparam T Type of the object to be inserted
    * @return The  [[BSONObjectID]] associated with the newly created document
    */
  private def insert[T](document: T, coll: BSONCollection)(implicit writer: Writer[T]): Unit = {

    val futureRes = coll.insert(document)
    val res = Await.result(futureRes, Duration.Inf)
    res.errmsg match {
      case Some(m) => logger.error(s"Failed to insert and create new id into $coll")
      case _ => // logging needed we won
    }
  }
}

/**
  * Companion object for the MongoDatabaseDriver class
  */
object MongoDatabaseDriver {

  /**
    * Factory constructor method for the mongoDB database driver
 *
    * @return a new instance of MongoDatabseDriver
    */
  def apply(): MongoDatabaseDriver = new MongoDatabaseDriver()
}
