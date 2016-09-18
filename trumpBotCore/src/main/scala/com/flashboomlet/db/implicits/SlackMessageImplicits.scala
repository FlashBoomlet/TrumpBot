package com.flashboomlet.db.implicits

import java.util.Date

import _root_.reactivemongo.bson.BSONInteger
import com.flashboomlet.data.SlackMessage
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Slack Message model in of MongoDB */
trait SlackMessageImplicits extends MongoConstants {

  /** Implicit conversion object for Slack Message writing */
  implicit object SlackMessageWriter extends BSONDocumentWriter[SlackMessage] {

    override def write(msg: SlackMessage): BSONDocument = BSONDocument(
      SlackMessageConstants.Id -> BSONInteger(msg.id),
      SlackMessageConstants.Channel -> BSONString(msg.channel),
      SlackMessageConstants.Text -> BSONString(msg.text),
      SlackMessageConstants.User -> BSONString(msg.user),
      SlackMessageConstants.Time -> BSONDateTime(msg.time),
      SlackMessageConstants.Response -> BSONString(msg.response)
    )
  }


  /** Implicit conversion object for reading Slack Message class */
  implicit object SlackMessageReader extends BSONDocumentReader[SlackMessage] {

    override def read(doc: BSONDocument): SlackMessage = {
      val id = doc.getAs[Int](SlackMessageConstants.Id).get
      val channel = doc.getAs[String](SlackMessageConstants.Channel).get
      val text = doc.getAs[String](SlackMessageConstants.Text).get
      val user = doc.getAs[String](SlackMessageConstants.User).get
      val time = doc.getAs[Date](SlackMessageConstants.Time).get.getTime
      val response = doc.getAs[String](SlackMessageConstants.Response).get

      SlackMessage(
        id = id,
        channel = channel,
        text = text,
        user = user,
        time = time,
        response = response
      )
    }
  }
}
