package com.flashboomlet.preprocessing

import java.util.Properties

import edu.stanford.nlp.pipeline.StanfordCoreNLP

/**
  * Contains factory methods for constructing stanford NLP clients.
  */
object NLPClientFactory {

  /**
    * Factory method for constructing what we would call a default configuration for our
    * particular NLP needs.
    * @return StanfordCoreNLP client used for extracting NLP data
    */
  def defaultNounParser(): StanfordCoreNLP = {
    val properties: Properties = new Properties()
    properties.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref")
    new StanfordCoreNLP(properties)
  }
}
