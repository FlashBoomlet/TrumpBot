package com.flashboomlet.preprocessing

import com.fasterxml.jackson.databind.ObjectMapper

import scalaj.http.Http


/** Object containing REST call to analyze sentiment of text */
object FastSentimentClassifier {

  private[this] val RequestLocation = "http://localhost:8081/web/text/"

  /**
    * Gets the sentiment of a piece of text by querying the fast sentiment classifier rest api.
    *
    * @param text Text to be analyzed
    * @param mapper Object mapper to parse JSON response
    * @return Future sentiment Data
    */
  def getSentiment(text: String)(implicit mapper: ObjectMapper): Sentiment = {
    val request = Http(RequestLocation).postForm(Seq("txt" -> text)).asString.body
    mapper.readValue(request,classOf[Sentiment])
  }
}