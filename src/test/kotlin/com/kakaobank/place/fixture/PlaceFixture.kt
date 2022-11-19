package com.kakaobank.place.fixture

import com.kakaobank.place.domain.Place

object PlaceFixture {
    fun getPlaces(num: Int = 10): List<Place> {
        return (1..10).map { Place(it.toString()) }
    }
}
