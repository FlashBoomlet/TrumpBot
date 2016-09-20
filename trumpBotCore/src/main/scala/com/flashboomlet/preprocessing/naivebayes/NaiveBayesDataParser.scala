package com.flashboomlet.preprocessing.naivebayes

import java.io.File

import com.github.tototoshi.csv.CSVReader
import com.github.tototoshi.csv.DefaultCSVFormat

/**
  * Parser resource naive bayes classifier data
  */
object NaiveBayesDataParser {

  /**
    * Used so common speech using commas will not be incorrectly delimited
    */
  implicit object CustomCSVFormat extends DefaultCSVFormat {
    override val delimiter = '~'
  }

  /* Name containing Data to teach bayes with. */
  val BayesDataCSV = "bayesData.csv"
  val file = new File(BayesDataCSV)
  val reader = CSVReader.open(file)(CustomCSVFormat)

  /**
    * Load all of the data to teach the bayes classifier from the CSV resource file.
    * @return Vector of k,v pairs of topic to text
    */
  def loadTeachings(): List[(String, String)] = {
    reader.all().map{ line =>
      (line.head, line(1))
    }
  }

  /**
    * Gets a list of the topics from the bayesData.csv
    * @return List of classifiable topics.
    */
  def getTopics: List[String] = {
    reader.all().map(_.head)
  }
}
