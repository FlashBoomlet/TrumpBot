package com.flashboomlet.db.implicits

import com.flashboomlet.data.Response
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Slack Message model in of MongoDB */
trait ResponseImplicits extends MongoConstants {

  /** Implicit conversion object for Slack Message writing */
  implicit object ResponseWriter extends BSONDocumentWriter[Response] {

    override def write(msg: Response): BSONDocument = BSONDocument(
      ResponseConstants.Content -> BSONString(msg.content),
      ResponseConstants.CannedTrigger -> BSONString(msg.cannedTrigger),
      ResponseConstants.Topics -> msg.topics,
      ResponseConstants.Topic -> BSONString(msg.primaryTopic),
      ResponseConstants.ConversationState -> BSONInteger(msg.conversationState),
      ResponseConstants.Transitional -> BSONBoolean(msg.transitional),
      ResponseConstants.PartOfTopic -> msg.partOfTopic,
      ResponseConstants.PositiveSentiment -> BSONBoolean(msg.positiveSentiment),
      ResponseConstants.NegativeSentiment -> BSONBoolean(msg.negativeSentiment),
      ResponseConstants.TangentTopic -> BSONString(msg.tangentTopic)
    )
  }


  /** Implicit conversion object for reading Slack Message class */
  implicit object ResponseReader extends BSONDocumentReader[Response] {

    override def read(doc: BSONDocument): Response = {
      val content = doc.getAs[String](ResponseConstants.Content).get
      val cannedTrigger = doc.getAs[String](ResponseConstants.CannedTrigger).get
      val topics = doc.getAs[List[String]](ResponseConstants.Topics).get
      val topic = doc.getAs[String](ResponseConstants.Topic).get
      val conversationState = doc.getAs[Int](ResponseConstants.ConversationState).get
      val transitional = doc.getAs[Boolean](ResponseConstants.Transitional).get
      val partOfTopic = doc.getAs[List[String]](ResponseConstants.PartOfTopic).get
      val positiveSentiment = doc.getAs[Boolean](ResponseConstants.PositiveSentiment).get
      val negativeSentiment = doc.getAs[Boolean](ResponseConstants.NegativeSentiment).get
      val tangentTopic = doc.getAs[String](ResponseConstants.TangentTopic).get

      Response(
        content = content,
        cannedTrigger = cannedTrigger,
        topics = topics,
        primaryTopic = topic,
        conversationState = conversationState,
        transitional = transitional,
        partOfTopic = partOfTopic,
        positiveSentiment = positiveSentiment,
        negativeSentiment = negativeSentiment,
        tangentTopic = tangentTopic
      )
    }
  }
}
