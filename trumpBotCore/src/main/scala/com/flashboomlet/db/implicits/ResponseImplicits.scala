package com.flashboomlet.db.implicits

import com.flashboomlet.data.Response
import com.flashboomlet.db.MongoConstants
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
      ResponseConstants.Topics -> msg.topics,
      ResponseConstants.Topic -> BSONString(msg.topic),
      ResponseConstants.ConversationState -> BSONInteger(msg.conversationState),
      ResponseConstants.Transitional -> BSONInteger(msg.transitional),
      ResponseConstants.PartOfTopic -> msg.partOfTopic,
      ResponseConstants.PositiveSentiment -> BSONInteger(msg.positiveSentiment),
      ResponseConstants.NegativeSentiment -> BSONInteger(msg.negativeSentiment),
      ResponseConstants.TangentTopic -> BSONString(msg.tangentTopic)
    )
  }


  /** Implicit conversion object for reading Slack Message class */
  implicit object ResponseReader extends BSONDocumentReader[Response] {

    override def read(doc: BSONDocument): Response = {
      val content = doc.getAs[String](ResponseConstants.Content).get
      val topics = doc.getAs[Array[String]](ResponseConstants.Topics).get
      val topic = doc.getAs[String](ResponseConstants.Topic).get
      val conversationState = doc.getAs[Int](ResponseConstants.ConversationState).get
      val transitional = doc.getAs[Int](ResponseConstants.Transitional).get
      val partOfTopic = doc.getAs[Array[String]](ResponseConstants.PartOfTopic).get
      val positiveSentiment = doc.getAs[Int](ResponseConstants.PositiveSentiment).get
      val negativeSentiment = doc.getAs[Int](ResponseConstants.NegativeSentiment).get
      val tangentTopic = doc.getAs[String](ResponseConstants.TangentTopic).get

      Response(
        content = content,
        topics = topics,
        topic = topic,
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
