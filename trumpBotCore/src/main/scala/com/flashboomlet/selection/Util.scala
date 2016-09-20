package com.flashboomlet.selection

/** Simple utility */
object Util {


  /**
    * Calculate Average Sentiment is a helper function to calculate the average sentiment.
    *
    * This function finds the sum of the sums up each category and then divides by the total of all
    *   of the data
    *
    * @param sentiment a list of all of the sentiment
    * @return a double of the average sentiment
    */
  def calculateAverageSentiment(sentiment: List[(Long, String)]): Double = {
    val goldenRatio = 1.618
    val positive = getSumsAndCounts(
      sentiment.filter(s => s._2 == "Positive"))
    val negative = getSumsAndCounts(
      sentiment.filter(s => s._2 == "Negative"))
    val neutral = getSumsAndCounts(
      sentiment.filter(s => s._2 == "Neutral"))
    if (positive._2 + negative._2 != 0) {
      val w1 = (positive._1 - negative._1) / (positive._2 + negative._2)
      if (neutral._2 != 0) {
        val w2 = neutral._1 / neutral._2
        val w3 = neutral._2 / ((positive._2 + negative._2) + neutral._2)
        w1 * (1.0 - (w2 * Math.pow(w3, goldenRatio)))
      } else {
        w1 // we have no neutral sentiments
      }
    } else {
      0.0 // if there are no positive or negative sentiments, we have pure neutrality
    }
  }

  /**
    * Get Sums and Counts is a helper function to get the sums and counts of a sentiment
    *
    * @param sentiment the list of sentiment values to derive data on
    * @return the derived data, sums and counts (Sum of Sentiment, Count of Sentiment)
    */
  private def getSumsAndCounts(sentiment: List[(Long, String)]): (Double, Double) = {
    val sumAndCounts = sentiment.foldLeft[(Double, Double)]((0,0))( (acc, data) => {
      (acc._1 + data._1.toDouble, acc._2 + 1.0)
    })
    (sumAndCounts._1 / 100, sumAndCounts._2)
  }

}
