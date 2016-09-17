package com.flashboomlet.db

import com.typesafe.scalalogging.LazyLogging
import com.flashboomlet.data.ConversationState
import com.flashboomlet.db.implicits.MongoImplicits
import reactivemongo.api.BSONSerializationPack.Writer
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
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

  val db = connection(TrumpBotDatabaseString)

  val conversationStateCollection: BSONCollection = db(ConversationStateCollection)

  /**
    * Gets a conversation state given a conversation state id
    *
    * @param id the conversation id that corresponds to the current conversation.
    * @return the conversation state of the conversation
    */
  def getConversationState(id: Long): Option[ConversationState] ={
    val future: Future[Option[ConversationState]] = conversationStateCollection
      .find(BSONDocument(
        ConversationStateConstants.ConversationId -> id ))
      .cursor[ConversationState]().collect[List]()
      .map { list => list.headOption }

    Await.result(future, Duration.Inf)
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
      ConversationStateConstants.ConversationId -> cs.conversationId)
    conversationStateCollection.findAndUpdate(selector, cs).onComplete {
      case Success(result) => logger.info("successfully updated conversation state")
      case _ =>
        logger.error(s"failed to update the conversation state ${cs.conversationId}")
    }
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

    coll.insert(document).onComplete {
      case Failure(e) =>
        logger.error(s"Failed to insert and create new id into $coll")
        throw e// we fucked up
      case Success(writeResult) => // logging needed we won
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
  def apply(): MongoDatabaseDriver = new MongoDatabaseDriver
}
