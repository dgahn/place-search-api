package com.kakaobank.place.client.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import com.kakaobank.place.client.ResponseDto

@ResponseDto
data class KaKaoPlaceMetaDto(
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("pageable_count")
    val pageableCount: Int,
    @JsonProperty("is_end")
    val isEnd: Boolean,
    @JsonProperty("same_name")
    val regionInfo: KakaoRegionInfoDto
)

@ResponseDto
data class KakaoRegionInfoDto(
    @JsonProperty("region")
    val region: List<String>,
    @JsonProperty("keyword")
    val keyword: String,
    @JsonProperty("selected_region")
    val selectedRegion: String
)

@ResponseDto
data class KakaoDocumentDto(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("place_name")
    val placeName: String,
    @JsonProperty("category_name")
    val categoryName: String,
    @JsonProperty("category_group_code")
    val categoryGroupCode: String,
    @JsonProperty("category_group_name")
    val categoryGroupName: String,
    @JsonProperty("phone")
    val phone: String,
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("road_address_name")
    val roadAddressName: String,
    @JsonProperty("x")
    val x: String,
    @JsonProperty("y")
    val y: String,
    @JsonProperty("place_url")
    val placeUrl: String,
    @JsonProperty("distance")
    val distance: String
)

@ResponseDto
data class KakaoPlaceResponseDto(
    @JsonProperty("meta")
    val meta: KaKaoPlaceMetaDto,
    @JsonProperty("documents")
    val documents: List<KakaoDocumentDto>
)
