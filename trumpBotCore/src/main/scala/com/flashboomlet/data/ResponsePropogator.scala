package com.flashboomlet.data

import java.io.File

import com.flashboomlet.db.MongoDatabaseDriver
import com.github.tototoshi.csv.CSVReader
import com.github.tototoshi.csv.DefaultCSVFormat

/**
  * Parses and populates the database with responses
  */
object ResponsePropogator {
  /**
    * Used so common speech using commas will not be incorrectly delimited
    */
  implicit object CustomCSVFormat extends DefaultCSVFormat {
    override val delimiter = '~'
  }

  /* Name containing Data to teach bayes with. */
  val ResponsesCSV = "responses.csv"
  val file = new File(ResponsesCSV)
  val reader = CSVReader.open(file)(CustomCSVFormat)


  /**
    * Reads in in the responses and populates them.
    * @param driver DatabaseDriver to populate responses
    */
  def populateResponses(driver: MongoDatabaseDriver): Unit = {
    loadResponses().foreach { r =>
      driver.insertResponse(r)
    }
  }

  /**
    * Load all of the response data from the CSV file.
    *
    * @return Vector of k,v pairs of topic to text
    */
  private[this] def loadResponses(): List[Response] = {
    reader.allWithHeaders().map{ (fields: Map[String, String]) =>
      Response(
        content = fields.get("content").get,
        cannedTrigger = getFieldSafely(fields.get("cannedTrigger")),
        conversationState = fields.get("conversationState").get.toInt,
        transitional = fields.get("transitional").get.toInt match {
          case 0 => false
          case 1 => true
        },
        positiveSentiment = fields.get("positiveSentiment").get.toInt match {
          case 0 => false
          case 1 => true
        },
        negativeSentiment = fields.get("negativeSentiment").get.toInt match {
          case 0 => false
          case 1 => true
        },
        primaryTopic = getFieldSafely(fields.get("primaryTopic")),
        tangentTopic = getFieldSafely(fields.get("tangentTopic")),
        topics = fields.getOrElse("topics", "").split(',').toList,
        partOfTopic = fields.getOrElse("partOfTopic", "").split(',').toList
      )
    }
  }

  private[this] def getFieldSafely(maybeField: Option[String]): String = {
    maybeField match {
      case Some(f) =>
        if (f.length > 0) {
          f
        } else {
          "none"
        }
      case None => "none"
    }
  }
}
