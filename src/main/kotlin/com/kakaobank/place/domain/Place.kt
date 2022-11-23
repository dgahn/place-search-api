package com.kakaobank.place.domain

import com.kakaobank.place.util.similarity
import kotlin.math.abs

data class Place(
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val type: SearchType
) {
    private val trimName = name.replace(" ", "").trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Place

        if (abs(latitude - other.latitude) > GEO_INTERVAL_MINIMUM) return false
        if (abs(longitude - other.longitude) > GEO_INTERVAL_MINIMUM) return false
        if (trimName.similarity(other.trimName) < NAME_SIMILARITY_MINIMUM) return false

        return true
    }

    override fun hashCode(): Int {
        return (latitude * 100).toInt() * (longitude * 100).toInt()
    }

    companion object {
        private const val GEO_INTERVAL_MINIMUM = 0.0001
        private const val NAME_SIMILARITY_MINIMUM = 0.8
    }
}
