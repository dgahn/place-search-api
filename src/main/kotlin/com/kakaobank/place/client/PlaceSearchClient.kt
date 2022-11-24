package com.kakaobank.place.client

import com.kakaobank.place.client.kakao.KaKaoClient
import com.kakaobank.place.client.naver.NaverClient
import com.kakaobank.place.domain.Place
import org.springframework.stereotype.Component

@Component
class PlaceSearchClient(
    private val kakaoClient: KaKaoClient,
    private val naverClient: NaverClient
) {
    fun search(query: String): Set<Place> {
        val kakaoPlaces = runCatching { kakaoClient.search(query) }.getOrDefault(emptyList())
        val naverPlaces = runCatching { naverClient.search(query) }.getOrDefault(emptyList())
        return mutableSetOf<Place>().apply {
            this.addAll(kakaoPlaces)
            this.addAll(naverPlaces)
        }
    }
}
