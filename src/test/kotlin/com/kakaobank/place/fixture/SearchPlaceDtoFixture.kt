package com.kakaobank.place.fixture

import com.kakaobank.place.controller.dto.PlaceListResponseDto
import com.kakaobank.place.controller.dto.PlaceResponseDto
import com.kakaobank.place.domain.SearchType

object SearchPlaceDtoFixture {
    fun getResponseDto(num: Int = 10, type: SearchType = SearchType.KAKAO): PlaceListResponseDto {
        return PlaceListResponseDto((1..10).map { PlaceResponseDto(it.toString(), type) })
    }
}
