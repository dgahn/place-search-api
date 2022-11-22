package com.kakaobank.place.controller

import com.kakaobank.place.application.PlaceApplicationService
import com.kakaobank.place.fixture.PlaceFixture
import com.kakaobank.place.fixture.SearchPlaceDtoFixture
import com.kakaopay.honey.util.SpringMockMvcTestSupport
import com.kakaopay.honey.util.URI
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@WebMvcTest(value = [PlaceController::class])
class PlaceControllerTest : SpringMockMvcTestSupport() {

    @MockkBean
    lateinit var placeApplicationService: PlaceApplicationService

    @Test
    fun `장소를_검색할_수_있다`() {
        every { placeApplicationService.searchPlace(any()) } returns PlaceFixture.getPlaces()
        val uri = URI("/api/v1/place")
        val query = "query"
        uri.addQueryParam(query, query)
        val response = SearchPlaceDtoFixture.getResponseDto()
        mockMvcGetTest(uri, response)
    }
}
