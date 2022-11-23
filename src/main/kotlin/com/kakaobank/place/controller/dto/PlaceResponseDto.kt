package com.kakaobank.place.controller.dto

import com.kakaobank.place.domain.Place
import com.kakaobank.place.domain.SearchType

data class PlaceResponseDto(
    val title: String,
    val type: SearchType
)

data class PlaceListResponseDto(
    val list: List<PlaceResponseDto>
)

fun List<Place>.toPlaceListResponseDto(): PlaceListResponseDto {
    return PlaceListResponseDto(this.map { it.toPlaceResponseDto() })
}

fun Place.toPlaceResponseDto(): PlaceResponseDto {
    return PlaceResponseDto(name, type)
}
