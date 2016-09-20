package com.flashboomlet.db.implicits

import _root_.reactivemongo.bson.BSONInteger
import com.flashboomlet.data.ConversationState
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONBoolean
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait ConversationStateImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object ConversationStateWriter extends BSONDocumentWriter[ConversationState] {

    override def write(cs: ConversationState): BSONDocument = BSONDocument(
      ConversationStateConstants.ConversationId -> BSONLong(cs.conversationId),
      ConversationStateConstants.MessageId -> BSONInteger(cs.messageId),
      ConversationStateConstants.LengthState -> BSONInteger(cs.lengthState),
      ConversationStateConstants.SentimentConfidence -> BSONLong(cs.sentimentConfidence),
      ConversationStateConstants.SentimentClass -> BSONString(cs.sentimentClass),
      ConversationStateConstants.Topic -> BSONString(cs.topic),
      ConversationStateConstants.Topics -> cs.topic,
      ConversationStateConstants.ConversationState -> BSONInteger(cs.conversationState),
      ConversationStateConstants.TransitionState -> BSONBoolean(cs.transitionState),
      ConversationStateConstants.TopicResponseCount -> BSONInteger(cs.topicResponseCount),
      ConversationStateConstants.TroubleMode -> BSONBoolean(cs.troubleMode),
      ConversationStateConstants.EscapeMode -> BSONBoolean(cs.escapeMode),
      ConversationStateConstants.Tangent -> BSONBoolean(cs.tangent),
      ConversationStateConstants.ParentTopic -> BSONString(cs.parentTopic),
      ConversationStateConstants.Message -> BSONString(cs.message),
      ConversationStateConstants.ResponseMessage -> BSONString(cs.responseMessage),
      ConversationStateConstants.TangentCount -> BSONInteger(cs.tangentCount)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object ConversationStateReader extends BSONDocumentReader[ConversationState] {

    override def read(doc: BSONDocument): ConversationState = {
      val conversationId = doc.getAs[Long](ConversationStateConstants.ConversationId).get
      val messageId = doc.getAs[Int](ConversationStateConstants.MessageId).get
      val lengthState = doc.getAs[Int](ConversationStateConstants.LengthState).get
      val sentimentConf = doc.getAs[Long](ConversationStateConstants.SentimentConfidence).get
      val sentimentClass = doc.getAs[String](ConversationStateConstants.SentimentClass).get
      val topic = doc.getAs[String](ConversationStateConstants.Topic).get
      val topics = doc.getAs[List[String]](ConversationStateConstants.Topics).getOrElse(List())
      val conversationState = doc.getAs[Int](ConversationStateConstants.ConversationState).get
      val transitionState = doc.getAs[Boolean](ConversationStateConstants.TransitionState).get
      val topicResponseCount = doc.getAs[Int](ConversationStateConstants.TopicResponseCount).get
      val troubleMode = doc.getAs[Boolean](ConversationStateConstants.TroubleMode).get
      val escapeMode = doc.getAs[Boolean](ConversationStateConstants.EscapeMode).get
      val tangent = doc.getAs[Boolean](ConversationStateConstants.Tangent).get
      val parentTopic = doc.getAs[String](ConversationStateConstants.ParentTopic).get
      val message = doc.getAs[String](ConversationStateConstants.Message).get
      val responseMessage = doc.getAs[String](ConversationStateConstants.ResponseMessage).get
      val tangentCount = doc.getAs[Int](ConversationStateConstants.TangentCount).get


      ConversationState(
        conversationId = conversationId,
        messageId = messageId,
        lengthState = lengthState,
        sentimentConfidence = sentimentConf,
        sentimentClass = sentimentClass,
        topic = topic,
        topics = topics,
        conversationState = conversationState,
        transitionState = transitionState,
        topicResponseCount = topicResponseCount,
        troubleMode = troubleMode,
        escapeMode = escapeMode,
        tangent = tangent,
        parentTopic = parentTopic,
        message = message,
        responseMessage = responseMessage,
        tangentCount = tangentCount
      )
    }
  }
}
