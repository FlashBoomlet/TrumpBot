package com.flashboomlet.preprocessing

import scala.collection.JavaConversions._
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.pipeline.StanfordCoreNLP

import scala.annotation.tailrec

/**
  * Used for natural language processing utilities
  */
object NLPUtil {

  /**
    * Given tome text, this function parses the nouns and pronouns from the text.
    *
    * @param text provided text
    * @param pipeline implicit NLP Client pipline configured to parse data
    * @return Seq of words that are nouns or pronouns.
    */
  def getNouns(text: String)(implicit pipeline: StanfordCoreNLP): Seq[String] = {
    val document = new Annotation(text)
    pipeline.annotate(document)
    val coreLabels: Seq[CoreLabel] = document.get(
      classOf[SentencesAnnotation])
        .flatMap(_.get(classOf[TokensAnnotation]))
    accNouns(
      Seq(),
      coreLabels
    )
  }

  /**
    * @param accWords
    * Helper recursive for parsing the nouns and pronouns from the stanford parsed CoreLabels.
    *
    * @param labels The labels parsed by the NLP Client
    * @return Seq of words that are nouns and pronouns.
    */
  @tailrec
  private[this] def accNouns(accWords: Seq[String], labels: Seq[CoreLabel]): Seq[String] = {
    if (labels.isEmpty) {
      accWords
    } else {
      val headItem = labels.head
      val headSpeechParts = headItem.get(classOf[PartOfSpeechAnnotation])
      if (headSpeechParts.contains("NN") || headSpeechParts.contains("PRP") ) {
        accNouns(headItem.get(classOf[TextAnnotation]) +: accWords, labels.tail)
      } else {
        accNouns(accWords, labels.tail)
      }
    }
  }
}
