package com.flashboomlet.db.implicits

import com.flashboomlet.data.Response
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONLong

/** Implicit readers and writers for the Slack Message model in of MongoDB */
trait ResponseImplicits extends MongoConstants {

  /** Implicit conversion object for Slack Message writing */
  implicit object ResponseWriter extends BSONDocumentWriter[Response] {

    override def write(msg: Response): BSONDocument = BSONDocument(
      ResponseConstants.Id -> BSONLong(msg.id)
      // TODO: Add the rest of the constants
    )
  }


  /** Implicit conversion object for reading Slack Message class */
  implicit object ResponseReader extends BSONDocumentReader[Response] {

    override def read(doc: BSONDocument): Response = {
      val id = doc.getAs[Int](ResponseConstants.Id).get
      // TODO: Add the rest of the vals

      Response(
        id = id
        // TODO: Add the rest of the val assignments
      )
    }
  }
}
