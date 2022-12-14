package com.kakaobank.place.application

import com.kakaobank.place.client.PlaceSearchClient
import com.kakaobank.place.domain.Place
import com.kakaobank.place.domain.SearchPlaceHistory
import com.kakaobank.place.domain.SearchPlaceHistoryJpaRepository
import com.kakaobank.place.domain.SearchType
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceApplicationService(
    private val placeSearchClient: PlaceSearchClient,
    private val searchPlaceHistoryJpaRepository: SearchPlaceHistoryJpaRepository
) {
    @Transactional
    fun searchPlace(query: String): List<Place> {
        saveSearchPlaceHistory(query)
        return extract(placeSearchClient.search(query))
    }

    private fun saveSearchPlaceHistory(query: String) {
        val history = searchPlaceHistoryJpaRepository.findByIdOrNull(query)
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

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ["searchedTop"])
    fun searchTopPlace(size: Int): List<SearchPlaceHistory> {
        val pageRequest = PageRequest.of(0, size)
        return searchPlaceHistoryJpaRepository.findAllByOrderByCountDesc(pageRequest)
    }

    companion object {
        private const val SEARCH_BASE_SIZE = 5
        private const val TOTAL_SIZE = 10
        private const val SUB_FIRST_INDEX = 0
    }
}
