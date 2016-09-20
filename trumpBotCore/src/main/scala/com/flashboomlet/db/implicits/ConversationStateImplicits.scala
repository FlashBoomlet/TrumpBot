package com.flashboomlet.db.implicits

import _root_.reactivemongo.bson.BSONInteger
import com.flashboomlet.data.ConversationState
import com.flashboomlet.db.MongoConstants
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
      ConversationStateConstants.Sentiment -> BSONLong(cs.sentiment),
      ConversationStateConstants.Topic -> BSONString(cs.topic),
      ConversationStateConstants.Topics -> cs.topic,
      ConversationStateConstants.ConversationState -> BSONInteger(cs.conversationState),
      ConversationStateConstants.TransitionState -> BSONInteger(cs.transitionState),
      ConversationStateConstants.TopicResponseCount -> BSONInteger(cs.topicResponseCount),
      ConversationStateConstants.TroubleMode -> BSONInteger(cs.troubleMode),
      ConversationStateConstants.EscapeMode -> BSONInteger(cs.escapeMode),
      ConversationStateConstants.Tangent -> BSONInteger(cs.tangent),
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
      val sentiment = doc.getAs[Long](ConversationStateConstants.Sentiment).get
      val topic = doc.getAs[String](ConversationStateConstants.Topic).get
      val topics = doc.getAs[List[String]](ConversationStateConstants.Topics).get
      val conversationState = doc.getAs[Int](ConversationStateConstants.ConversationState).get
      val transitionState = doc.getAs[Int](ConversationStateConstants.TransitionState).get
      val topicResponseCount = doc.getAs[Int](ConversationStateConstants.TopicResponseCount).get
      val troubleMode = doc.getAs[Int](ConversationStateConstants.TroubleMode).get
      val escapeMode = doc.getAs[Int](ConversationStateConstants.EscapeMode).get
      val tangent = doc.getAs[Int](ConversationStateConstants.Tangent).get
      val parentTopic = doc.getAs[String](ConversationStateConstants.ParentTopic).get
      val message = doc.getAs[String](ConversationStateConstants.Message).get
      val responseMessage = doc.getAs[String](ConversationStateConstants.ResponseMessage).get
      val tangentCount = doc.getAs[Int](ConversationStateConstants.TangentCount).get


      ConversationState(
        conversationId = conversationId,
        messageId = messageId,
        lengthState = lengthState,
        sentiment = sentiment,
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
