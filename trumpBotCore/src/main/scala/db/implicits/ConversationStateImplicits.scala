package db.implicits

import _root_.reactivemongo.bson.BSONInteger
import data.ConversationState
import db.MongoConstants
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
      ConversationStateConstants.EmotionalState -> BSONString(cs.emotionalState),
      ConversationStateConstants.LengthState -> BSONInteger(cs.lengthState),
      ConversationStateConstants.Sentiment -> BSONLong(cs.sentiment),
      ConversationStateConstants.Topic -> BSONString(cs.topic),
      ConversationStateConstants.ConversationState -> BSONString(cs.conversationState),
      ConversationStateConstants.TransitionState -> BSONInteger(cs.transitionState),
      ConversationStateConstants.TopicResponseCount -> BSONInteger(cs.topicResponseCount),
      ConversationStateConstants.TroubleMode -> BSONInteger(cs.troubleMode),
      ConversationStateConstants.EscapeMode -> BSONInteger(cs.escapeMode),
      ConversationStateConstants.Tangent -> BSONInteger(cs.tangent),
      ConversationStateConstants.ParentTopic -> BSONString(cs.parentTopic)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object ConversationStateReader extends BSONDocumentReader[ConversationState] {

    override def read(doc: BSONDocument): ConversationState = {
      val conversationId = doc.getAs[Long](ConversationStateConstants.ConversationId).get
      val emotionalState = doc.getAs[String](ConversationStateConstants.EmotionalState).get
      val lengthState = doc.getAs[Int](ConversationStateConstants.LengthState).get
      val sentiment = doc.getAs[Long](ConversationStateConstants.Sentiment).get
      val topic = doc.getAs[String](ConversationStateConstants.Topic).get
      val conversationState = doc.getAs[String](ConversationStateConstants.ConversationState).get
      val transitionState = doc.getAs[Int](ConversationStateConstants.TransitionState).get
      val topicResponseCount = doc.getAs[Int](ConversationStateConstants.TopicResponseCount).get
      val troubleMode = doc.getAs[Int](ConversationStateConstants.TroubleMode).get
      val escapeMode = doc.getAs[Int](ConversationStateConstants.EscapeMode).get
      val tangent = doc.getAs[Int](ConversationStateConstants.Tangent).get
      val parentTopic = doc.getAs[String](ConversationStateConstants.ParentTopic).get


      ConversationState(
        conversationId = conversationId,
        emotionalState = emotionalState,
        lengthState = lengthState,
        sentiment = sentiment,
        topic = topic,
        conversationState = conversationState,
        transitionState = transitionState,
        topicResponseCount = topicResponseCount,
        troubleMode = troubleMode,
        escapeMode = escapeMode,
        tangent = tangent,
        parentTopic = parentTopic
      )
    }
  }
}
