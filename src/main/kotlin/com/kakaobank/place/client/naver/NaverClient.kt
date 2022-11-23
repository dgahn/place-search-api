package com.kakaobank.place.client.naver

import com.kakaobank.place.domain.Place
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets

@Component
class NaverClient(
    private val restTemplate: RestTemplate,
    private val naverProperties: NaverProperties
) {
    private val headers = HttpHeaders().apply {
        contentType = MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
        add(naverProperties.clientIdKey, naverProperties.clientIdValue)
        add(naverProperties.clientSecretKey, naverProperties.clientSecretValue)
    }

    @Retryable(maxAttempts = 2, backoff = Backoff(delay = 100))
    fun search(
        query: String,
        start: Int = 1,
        display: Int = 10
    ): List<Place> {
        val url = createURL(query, start, display)
        val entity: HttpEntity<String> = HttpEntity(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, NaverPlaceResponseDto::class.java).body
            .toDomain()
    }

    private fun createURL(query: String, start: Int, display: Int): String =
        UriComponentsBuilder.fromHttpUrl(naverProperties.url)
            .queryParam(PARAM_QUERY, query)
            .queryParam(PARAM_START, start)
            .queryParam(PARAM_DISPLAY, display)
            .build()
            .toUriString()

    companion object {
        private const val PARAM_QUERY = "query"
        private const val PARAM_START = "start"
        private const val PARAM_DISPLAY = "display"
    }
}
