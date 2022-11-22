package com.kakaobank.place.client.kakao

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
class KaKaoClient(
    private val restTemplate: RestTemplate,
    private val kakaoProperties: KakaoProperties
) {
    private val headers = HttpHeaders().apply {
        contentType = MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
        set(HttpHeaders.AUTHORIZATION, "${kakaoProperties.keyPrefix} ${kakaoProperties.apiKey}")
    }

    @Retryable(maxAttempts = 2, backoff = Backoff(delay = 100))
    fun search(query: String): KakaoPlaceResponseDto {
        val url = createURL(query)
        val entity: HttpEntity<String> = HttpEntity(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, KakaoPlaceResponseDto::class.java).body
    }

    private fun createURL(query: String): String =
        UriComponentsBuilder.fromHttpUrl(kakaoProperties.url)
            .queryParam(PARAM_QUERY, query)
            .build()
            .toUriString()

    companion object {
        private const val PARAM_QUERY = "query"
    }
}
