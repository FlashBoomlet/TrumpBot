package com.flashboomlet.preprocessing.naivebayes

/**
  * Factory for creating out of the box naive bayes classifier implementation
  */
object NaiveBayesClassifierFactory {

  /**
    * Initializes and loads the classifier
    *
    * @return Classifier
    */
  def loadClassifier(): WrappedClassifier = {
    val initClassifier: Classifier = new Classifier()
    NaiveBayesDataParser.loadTeachings().foreach{ lesson =>
      initClassifier.train(lesson._1, lesson._2)
    }
    WrappedClassifier(classifier = initClassifier, topics = NaiveBayesDataParser.getTopics)
  }
}
