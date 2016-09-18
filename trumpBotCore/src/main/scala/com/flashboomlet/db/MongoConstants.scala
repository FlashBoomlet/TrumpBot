package com.flashboomlet.db

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

  /* String constant for the conversation state collection */
  final val ConversationStateCollection = "conversationStates"

  /* String constant for the slack message collection */
  final val SlackMessageCollection = "slackMessage"

  /* String constant for the slack message collection */
  final val ResponseCollection = "responseMessage"


  /**
    * String constants used in the conversation state collection model schema
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
    * String constants used in the slack message collection model schema
    */
  object SlackMessageConstants {

    /* String constant for the `Id` field in a MongoDB SlackMessage document */
    final val Id = "id"

    /* String constant for the `channel` field in a MongoDB SlackMessage document */
    final val Channel = "channel"

    /* String constant for the `text` field in a MongoDB SlackMessage document */
    final val Text = "text"

    /* String constant for the `user` field in a MongoDB SlackMessage document */
    final val User = "user"

    /* String constant for the `time` field in a MongoDB SlackMessage document */
    final val Time = "time"

    /* String constant for the `response` field in a MongoDB SlackMessage document */
    final val Response = "response"

  }

  /**
    * String constants used in the response collection model schema
    */
  object ResponseConstants {

    /* String constant for the `content` field in a MongoDB Response document */
    final val Content = "content"

    /* String constant for the `topics` field in a MongoDB Response document */
    final val Topics = "topics"

    /* String constant for the `topics` field in a MongoDB Response document */
    final val Topic = "topic"

    /* String constant for the `conversationstate` field in a MongoDB Response document */
    final val ConversationState = "conversationstate"

    /* String constant for the `transitional` field in a MongoDB Response document */
    final val Transitional = "transitional"

    /* String constant for the `partoftopic` field in a MongoDB Response document */
    final val PartOfTopic = "partoftopic"

    /* String constant for the `positivesentiment` field in a MongoDB Response document */
    final val PositiveSentiment = "positivesentiment"

    /* String constant for the `negativesentiment` field in a MongoDB Response document */
    final val NegativeSentiment = "negativesentiment"

    /* String constant for the `tangenttopic` field in a MongoDB Response document */
    final val TangentTopic = "tangenttopic"
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
