package com.kakaobank.place.controller.dto

import com.kakaobank.place.domain.Place

data class PlaceResponseDto(
    val title: String
)

data class PlaceListResponseDto(
    val list: List<PlaceResponseDto>
)

fun List<Place>.toPlaceListResponseDto(): PlaceListResponseDto {
    return PlaceListResponseDto(this.map { it.toPlaceResponseDto() })
}

fun Place.toPlaceResponseDto(): PlaceResponseDto {
    return PlaceResponseDto(title)
}
