package com.kakaobank.place.exception

import com.kakaobank.place.application.PlaceApplicationService
import com.kakaobank.place.controller.PlaceController
import com.kakaopay.honey.util.SpringMockMvcTestSupport
import com.kakaopay.honey.util.URI
import com.kakaopay.honey.util.toJson
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension::class)
@WebMvcTest(value = [PlaceController::class])
class ExceptionHandlerTest : SpringMockMvcTestSupport() {

    @MockkBean
    lateinit var placeApplicationService: PlaceApplicationService

    @BeforeEach
    fun setup() {
        val now = LocalDateTime.now()
        mockkStatic("java.time.LocalDateTime")
        every { LocalDateTime.now() } returns now
    }

    @AfterEach
    fun clear() {
        unmockkStatic("java.time.LocalDateTime")
    }

    @Test
    fun `IllegalStateException이_발생하면_BadRequest를_반환한다`() {
        val exception = IllegalStateException()
        every { placeApplicationService.searchTopPlace(any()) } throws exception
        val uri = URI("/api/v1/places/searched-top")
        mockMvcGetTest(
            uri = uri,
            expectedResponseDto = ErrorResponseDto.of(exception),
            status = HttpStatus.BAD_REQUEST
        )
    }

    @Test
    fun `IllegalArgumentException이_발생하면_BadRequest를_반환한다`() {
        val exception = IllegalArgumentException()
        every { placeApplicationService.searchTopPlace(any()) } throws exception
        val uri = URI("/api/v1/places/searched-top")
        mockMvcGetTest(
            uri = uri,
            expectedResponseDto = ErrorResponseDto.of(exception),
            status = HttpStatus.BAD_REQUEST
        )
    }

    @Test
    fun `ConstraintViolationException이_발생하면_BadRequest를_반환한다`() {
        val exception = ConstraintViolationException(setOf())
        every { placeApplicationService.searchTopPlace(any()) } throws exception
        val uri = URI("/api/v1/places/searched-top")
        mockMvcGetTest(
            uri = uri,
            expectedResponseDto = ErrorResponseDto.of(exception, listOf<String>().toJson()),
            status = HttpStatus.BAD_REQUEST
        )
    }
}
