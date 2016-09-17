package db

/**
  * Yeah, that's right. We got dank mongo constants
  *
  * The val's name must be HumpBack case
  * The string constant must be ALL lower case
  *
  * Collections must correspond
  */
trait MongoConstants {

  // DATABASE CONFIGURATION VALUES //

  /* String constant for connecting to MongoDB */
  final val DatabaseIp = "localhost"

  /* String constant for the database name */
  final val TrumpBotDatabaseString = "trumpBot"

  /* String constant for the new york times article collection */
  final val ConversationStateCollection = "conversationStates"


  /**
    * String constants used in the New York Times Articles collection model schema
    */
  object ConversationStateConstants {

    /* String constant for the `ConversationId` field in a MongoDB ConversationState document */
    final val ConversationId = "conversationid"

    /* String constant for the `EmotionalState` field in a MongoDB ConversationState document */
    final val EmotionalState = "emotionalstate"

    /* String constant for the `LengthState` field in a MongoDB ConversationState document */
    final val LengthState = "lengthstate"

    /* String constant for the `Sentiment` field in a MongoDB ConversationState document */
    final val Sentiment = "sentiment"

    /* String constant for the `Topic` field in a MongoDB ConversationState document */
    final val Topic = "topic"

    /* String constant for the `ConversationState` field in a MongoDB ConversationState document */
    final val ConversationState = "conversationstate"

    /* String constant for the `TransitionState` field in a MongoDB ConversationState document */
    final val TransitionState = "transitionstate"

    /* String constant for the `TopicResponseCount` field in a MongoDB ConversationState document */
    final val TopicResponseCount = "topicresponsecount"

    /* String constant for the `TroubleMode` field in a MongoDB ConversationState document */
    final val TroubleMode = "troublemode"

    /* String constant for the `EscapeMode` field in a MongoDB ConversationState document */
    final val EscapeMode = "escapemode"

    /* String constant for the `Tangent` field in a MongoDB ConversationState document */
    final val Tangent = "tangent"

    /* String constant for the `ParentTopic` field in a MongoDB ConversationState document */
    final val ParentTopic = "parenttopic"
  }


  /**
    * String constants used across the entirity of the MongoDB database
    */
  object GlobalConstants {

    /* String constant for the `_id` field in a MongoDB document */
    final val IdString = "_id"

    final val SetString = "$set"

    final val ElemMatchString = "$elemMatch"
  }

}
