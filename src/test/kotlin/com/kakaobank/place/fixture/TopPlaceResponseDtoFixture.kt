package com.kakaobank.place.fixture

import com.kakaobank.place.controller.dto.TopPlaceListResponseDto
import com.kakaobank.place.controller.dto.TopPlaceResponseDto

object TopPlaceResponseDtoFixture {
    fun getTopPlaceListResponseDto(num: Int = 10): TopPlaceListResponseDto {
        return TopPlaceListResponseDto(
            (1..num).map {
                TopPlaceResponseDto(
                    rank = it,
                    keyword = it.toString(),
                    count = it
                )
            }
        )
    }
}
