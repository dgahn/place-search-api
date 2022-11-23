package com.kakaobank.place.util

import java.util.Locale

fun String.similarity(s2: String): Double {
    var longer = this
    var shorter = s2
    if (this.length < s2.length) {
        longer = s2
        shorter = this
    }
    val longerLength = longer.length
    return if (longerLength == 0) 1.0 else (longerLength - editDistance(longer, shorter)) / longerLength.toDouble()
}

@Suppress("NestedBlockDepth")
private fun editDistance(s1: String, s2: String): Int {
    val s1LowerCase = s1.lowercase(Locale.getDefault())
    val s2LowerCase = s2.lowercase(Locale.getDefault())
    val costs = IntArray(s2LowerCase.length + 1)
    for (i in 0..s1LowerCase.length) {
        var lastValue = i
        for (j in 0..s2LowerCase.length) {
            if (i == 0) {
                costs[j] = j
            } else {
                if (j > 0) {
                    var newValue = costs[j - 1]
                    if (s1LowerCase[i - 1] != s2LowerCase[j - 1]) {
                        newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1
                    }
                    costs[j - 1] = lastValue
                    lastValue = newValue
                }
            }
        }
        if (i > 0) costs[s2LowerCase.length] = lastValue
    }
    return costs[s2LowerCase.length]
}
