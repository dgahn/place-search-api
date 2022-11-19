package com.kakaobank.place.fixture

import com.kakaobank.place.controller.dto.PlaceListResponseDto
import com.kakaobank.place.controller.dto.PlaceResponseDto

object SearchPlaceDtoFixture {
    fun getResponseDto(num: Int = 10): PlaceListResponseDto {
        return PlaceListResponseDto((1..10).map { PlaceResponseDto(it.toString()) })
    }
}
