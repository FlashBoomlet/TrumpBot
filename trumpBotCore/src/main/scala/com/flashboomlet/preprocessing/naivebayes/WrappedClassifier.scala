package com.flashboomlet.preprocessing.naivebayes

/**
  * Wrapper for the BayesClassifier that may be used so the topics are readily accessible.
 *
  * @param classifier Naive bayes classifier
  * @param topics List of possibly classified topics.
  */
class WrappedClassifier(val classifier: Classifier, val topics: List[String])

/**
  * Companion object containing constructor/ factory methods.
  */
object WrappedClassifier {

  def apply(classifier: Classifier, topics: List[String]) =
    new WrappedClassifier(classifier, topics)
}