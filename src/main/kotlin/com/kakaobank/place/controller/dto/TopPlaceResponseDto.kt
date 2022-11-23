package com.kakaobank.place.controller.dto

import com.kakaobank.place.domain.SearchPlaceHistory

data class TopPlaceResponseDto(
    val rank: Int,
    val keyword: String,
    val count: Int
)

data class TopPlaceListResponseDto(
    val list: List<TopPlaceResponseDto>
)

fun List<SearchPlaceHistory>.toTopPlaceListResponseDto(): TopPlaceListResponseDto {
    return TopPlaceListResponseDto(
        list = this.mapIndexed { index, searchPlaceHistory ->
            searchPlaceHistory.toTopPlaceResponseDto(index + 1)
        }
    )
}

fun SearchPlaceHistory.toTopPlaceResponseDto(rank: Int): TopPlaceResponseDto {
    return TopPlaceResponseDto(keyword = this.keyword, rank = rank, count = this.count)
}
