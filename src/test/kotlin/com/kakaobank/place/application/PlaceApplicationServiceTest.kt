package com.kakaobank.place.application

import com.kakaobank.place.client.PlaceSearchClient
import com.kakaobank.place.domain.Place
import com.kakaobank.place.domain.SearchPlaceHistoryJpaRepository
import com.kakaobank.place.domain.SearchType
import com.kakaobank.place.fixture.PlaceFixture
import com.kakaobank.place.fixture.SearchPlaceHistoryFixture
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(value = [PlaceApplicationService::class])
class PlaceApplicationServiceTest(
    @Autowired
    private val placeApplicationService: PlaceApplicationService
) {
    @MockkBean
    lateinit var placeSearchClient: PlaceSearchClient

    @MockkBean
    lateinit var searchPlaceHistoryJpaRepository: SearchPlaceHistoryJpaRepository

    @BeforeEach
    fun setUp() {
        every { searchPlaceHistoryJpaRepository.findByIdOrNull(any()) } returns null
        every { searchPlaceHistoryJpaRepository.save(any()) } returns SearchPlaceHistoryFixture.getSearchPlaceHistory()
    }

    @Test
    fun `카카오_결과와_네이버_결과가_각각_5개이하면_전부_합한다`() {
        val kakaoSize = 4
        val naverSize = 4
        val expected = kakaoSize + naverSize
        every { placeSearchClient.search(any()) } returns getPlaceSet(naverSize, kakaoSize)
        val actual = placeApplicationService.searchPlace("query").size
        actual shouldBe expected
    }

    private fun getPlaceSet(naverSize: Int, kakaoSize: Int): Set<Place> {
        val kakaoPlaces = PlaceFixture.getPlaces(
            num = kakaoSize,
            name = "kakao",
            type = SearchType.KAKAO
        )
        val naverPlaces = PlaceFixture.getPlaces(
            num = naverSize,
            name = "naver",
            type = SearchType.NAVER
        )
        return mutableSetOf<Place>().apply {
            this.addAll(kakaoPlaces)
            this.addAll(naverPlaces)
        }
    }

    @Test
    fun `카카오_결과가_5개_이하_네이버가_결과가_5개_초과_총_갯수가_10개_이하면_합한만큼_반환한다`() {
        val kakaoSize = 3
        val naverSize = 6
        val expected = kakaoSize + naverSize
        every { placeSearchClient.search(any()) } returns getPlaceSet(naverSize, kakaoSize)
        val actual = placeApplicationService.searchPlace("query").size
        actual shouldBe expected
    }

    @Test
    fun `카카오_결과가_5개_이하_네이버가_결과가_5개_초과_총_갯수가_10개_초과하면_10개를_반환한다`() {
        val kakaoSize = 3
        val naverSize = 8
        val expected = 10
        every { placeSearchClient.search(any()) } returns getPlaceSet(naverSize, kakaoSize)
        val actual = placeApplicationService.searchPlace("query").size
        actual shouldBe expected
    }

    @Test
    fun `카카오_결과가_5개_초과_네이버가_결과가_5개_초과인_경우_10개를_반환한다`() {
        val kakaoSize = 8
        val naverSize = 8
        val expected = 10
        every { placeSearchClient.search(any()) } returns getPlaceSet(naverSize, kakaoSize)
        val actual = placeApplicationService.searchPlace("query").size
        actual shouldBe expected
    }

    @Test
    fun `카카오_결과가_5개_초과_네이버_결과가_5개_이하_총_갯수가_10개_이하_합한만큼_반환한다`() {
        val kakaoSize = 6
        val naverSize = 3
        val expected = kakaoSize + naverSize
        every { placeSearchClient.search(any()) } returns getPlaceSet(naverSize, kakaoSize)
        val actual = placeApplicationService.searchPlace("query").size
        actual shouldBe expected
    }

    @Test
    fun `카카오_결과가_5개_초과_네이버_결과가_5개_이하_총_갯수가_10개_이상이면_10개를_반환한다`() {
        val kakaoSize = 6
        val naverSize = 5
        val expected = 10
        every { placeSearchClient.search(any()) } returns getPlaceSet(naverSize, kakaoSize)
        val actual = placeApplicationService.searchPlace("query").size
        actual shouldBe expected
    }
}
