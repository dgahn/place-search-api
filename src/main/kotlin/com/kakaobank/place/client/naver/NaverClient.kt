package com.kakaobank.place.client.naver

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
    fun search(query: String): NaverPlaceResponseDto {
        val url = createURL(query)
        val entity: HttpEntity<String> = HttpEntity(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, NaverPlaceResponseDto::class.java).body
    }

    private fun createURL(query: String): String =
        UriComponentsBuilder.fromHttpUrl(naverProperties.url)
            .queryParam(PARAM_QUERY, query)
            .build()
            .toUriString()

    companion object {
        private const val PARAM_QUERY = "query"
    }
}
