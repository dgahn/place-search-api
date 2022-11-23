package com.kakaobank.place.client.naver

import com.fasterxml.jackson.annotation.JsonProperty
import com.kakaobank.place.client.ResponseDto
import com.kakaobank.place.domain.Place
import com.kakaobank.place.domain.SearchType
import com.kakaobank.place.geo.GeoTrans
import com.kakaobank.place.geo.GeoTransPoint

@ResponseDto
data class NaverPlaceResponseDto(
    @JsonProperty("lastBuildDate")
    val lastBuildDate: String,
    @JsonProperty("total")
    val total: Int,
    @JsonProperty("start")
    val start: Int,
    @JsonProperty("display")
    val display: Int,
    @JsonProperty("items")
    val items: List<NaverItemDto>
) {
    fun toDomain(): List<Place> {
        return this.items.map { it.toDomain() }
    }
}

@ResponseDto
data class NaverItemDto(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("link")
    val link: String,
    @JsonProperty("category")
    val category: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("telephone")
    val telephone: String,
    @JsonProperty("address")
    val address: String,
    @JsonProperty("roadAddress")
    val roadAddress: String,
    @JsonProperty("mapx")
    val mapX: String,
    @JsonProperty("mapy")
    val mapY: String
) {
    fun toDomain(): Place {
        val katecPoint = GeoTransPoint(mapX.toDouble(), mapY.toDouble())
        val geoPoint = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, katecPoint)
        return Place(
            name = title.replace(HTML_REGEX, ""),
            longitude = geoPoint.y,
            latitude = geoPoint.x,
            type = SearchType.NAVER
        )
    }

    companion object {
        private const val HTML_REGEX_PATTERN = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>"
        private val HTML_REGEX = Regex(HTML_REGEX_PATTERN)
    }
}
