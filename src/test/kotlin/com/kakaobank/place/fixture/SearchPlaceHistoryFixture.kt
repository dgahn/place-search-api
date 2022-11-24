package com.kakaobank.place.fixture

import com.kakaobank.place.domain.SearchPlaceHistory

object SearchPlaceHistoryFixture {
    fun getSearchPlaceHistories(num: Int = 10): List<SearchPlaceHistory> {
        return (1..num).map {
            SearchPlaceHistory(
                keyword = it.toString()
            ).apply {
                (1..it).forEach { this.countUp() }
            }
        }
    }

    fun getSearchPlaceHistory(): SearchPlaceHistory {
        return SearchPlaceHistory(
            keyword = 1.toString()
        ).apply {
            this.countUp()
        }
    }
}
