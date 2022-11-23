package com.kakaobank.place.fixture

import com.kakaobank.place.domain.Place
import com.kakaobank.place.domain.SearchType

object PlaceFixture {
    fun getPlaces(num: Int = 10, name: String = "", type: SearchType = SearchType.KAKAO): List<Place> {
        return (1..num).map {
            Place(
                name = name + it.toString(),
                longitude = it.toDouble(),
                latitude = it.toDouble(),
                type = type
            )
        }
    }
}
