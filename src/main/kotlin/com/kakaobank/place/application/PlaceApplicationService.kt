package com.kakaobank.place.application

import com.kakaobank.place.client.kakao.KaKaoClient
import com.kakaobank.place.client.naver.NaverClient
import com.kakaobank.place.domain.Place
import com.kakaobank.place.domain.SearchPlaceHistory
import com.kakaobank.place.domain.SearchPlaceHistoryJpaRepository
import com.kakaobank.place.domain.SearchType
import org.springframework.stereotype.Service

@Service
class PlaceApplicationService(
    private val kakaoClient: KaKaoClient,
    private val naverClient: NaverClient,
    private val searchPlaceHistoryJpaRepository: SearchPlaceHistoryJpaRepository
) {
    fun searchPlace(query: String): List<Place> {
        val kakaoPlaces = kakaoClient.search(query)
        val naverPlaces = naverClient.search(query)
        val placeSet = mutableSetOf<Place>()
        placeSet.addAll(kakaoPlaces)
        placeSet.addAll(naverPlaces)
        countSearchPlaceHistory(query)
        return extract(placeSet)
    }

    private fun countSearchPlaceHistory(query: String) {
        val history = searchPlaceHistoryJpaRepository.findByKeyword(query)
            ?: SearchPlaceHistory(keyword = query)

        history.countUp()
        searchPlaceHistoryJpaRepository.save(history)
    }

    private fun extract(placeSet: Set<Place>): List<Place> {
        val (kakaoPlaces, naverPlaces) = placeSet.partition { it.type == SearchType.KAKAO }
        val kakaoSize = kakaoPlaces.size
        val naverSize = naverPlaces.size
        return when {
            kakaoSize <= SEARCH_BASE_SIZE && naverSize <= SEARCH_BASE_SIZE -> {
                kakaoPlaces.plus(naverPlaces)
            }

            kakaoSize <= SEARCH_BASE_SIZE && naverSize > SEARCH_BASE_SIZE -> {
                val total = kakaoPlaces.plus(naverPlaces)
                if (total.size <= TOTAL_SIZE) total else total.subList(SUB_FIRST_INDEX, TOTAL_SIZE)
            }

            naverSize > SEARCH_BASE_SIZE -> {
                kakaoPlaces.subList(SUB_FIRST_INDEX, SEARCH_BASE_SIZE)
                    .plus(naverPlaces.subList(SUB_FIRST_INDEX, SEARCH_BASE_SIZE))
            }

            else -> {
                if (kakaoSize + naverSize > TOTAL_SIZE) {
                    kakaoPlaces.subList(SUB_FIRST_INDEX, TOTAL_SIZE - naverSize).plus(naverPlaces)
                } else {
                    kakaoPlaces.plus(naverPlaces)
                }
            }
        }
    }

    fun searchTopPlace(size: Int): List<SearchPlaceHistory> {
        throw NotImplementedError("")
    }

    companion object {
        private const val SEARCH_BASE_SIZE = 5
        private const val TOTAL_SIZE = 10
        private const val SUB_FIRST_INDEX = 0
    }
}
