package com.kakaobank.place.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PlaceTest {

    @Test
    fun `좌표와_이름_유사도에_따라_같은_place로_취급한다`() {
        val place1 = Place(
            name = "대전 고용복지플러스센터",
            longitude = 36.34643613994802,
            latitude = 127.38558330241104,
            type = SearchType.KAKAO
        )
        val place2 = Place(
            name = "대전고용복지플러스센터",
            longitude = 36.34648549774588,
            latitude = 127.38559404531173,
            type = SearchType.NAVER
        )

        val set = setOf(place1, place2)
        set.size shouldBe 1
    }
}
