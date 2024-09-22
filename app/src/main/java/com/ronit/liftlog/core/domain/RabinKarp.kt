package com.ronit.liftlog.core.domain

fun rabinKarpSimilarity(_text: String, _pattern: String, prime: Int = 101): Int {

    val text = _text.lowercase().trim()
    val pattern = _pattern.lowercase().trim()
    val n = text.length
    val m = pattern.length

    if (m == 0 || n == 0) return 0  // Edge case: empty strings

    val patternHash = pattern.hashCodeWithPrime(m, prime)
    var textHash = text.substring(0, m.coerceAtMost(n)).hashCodeWithPrime(m, prime)

    var maxSimilarityScore = 0

    for (i in 0..(n - m)) {
        if (patternHash == textHash) {
            // Count the number of matching characters
            val currentScore = countMatchingChars(text.substring(i, i + m), pattern)
            maxSimilarityScore = maxSimilarityScore.coerceAtLeast(currentScore)
        }

        if (i < n - m) {
            textHash = text.updateHashForNextChar(i, i + m, textHash, m, prime)
        }
    }

    return maxSimilarityScore
}

fun countMatchingChars(text: String, pattern: String): Int {
    var score = 0
    for (i in text.indices) {
        if (i < pattern.length && text[i] == pattern[i]) {
            score++
        }
    }
    return score
}

fun String.hashCodeWithPrime(length: Int, prime: Int): Int {
    var hash = 0
    for (i in indices) {
        if (i >= this.length) break  // Ensure you don't exceed the string length
        hash = (prime * hash + this[i].code)
    }
    return hash
}

fun String.updateHashForNextChar(start: Int, end: Int, oldHash: Int, length: Int, prime: Int): Int {
    val newHash = (256 * (oldHash - this[start].code * Math.pow(256.0, (length - 1).toDouble()).toInt()) + this[end].code) % prime
    return if (newHash < 0) newHash + prime else newHash
}


