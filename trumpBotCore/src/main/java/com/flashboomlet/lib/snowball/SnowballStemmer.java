/**
 * ALL RIGHTS AND CREDIT GO TO:
 * Copyright (c) 2012 Alexandru Nedelcu
 * Copyright (c) 2012 Arnaud Legendre
 *
 * Thanks, guys. Source code provided from:
 *   https://github.com/arnaudleg/naive-bayes-classifier-scala
 */

package com.flashboomlet.lib.snowball;

public abstract class SnowballStemmer extends SnowballProgram {
    public abstract boolean stem();
}
