package com.kakaopay.honey.util

import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.nio.charset.StandardCharsets

@AutoConfigureMockMvc
abstract class SpringMockMvcTestSupport {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected fun <T> mockMvcGetTest(
        uri: URI,
        expectedResponseDto: T,
        customHeaders: HttpHeaders = HttpHeaders.EMPTY,
        status: HttpStatus = HttpStatus.OK
    ) {
        mockMvc.perform(
            MockMvcRequestBuilders.get(uri.value)
                .accept(MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .headers(customHeaders)
        )
            .andExpect { result -> result.response.status shouldBe status.value() }
            .andExpect(MockMvcResultMatchers.content().string(expectedResponseDto.toJson()))
    }
}
