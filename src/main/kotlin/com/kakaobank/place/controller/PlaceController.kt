package com.kakaobank.place.controller

import com.kakaobank.place.application.PlaceApplicationService
import com.kakaobank.place.controller.dto.PlaceListResponseDto
import com.kakaobank.place.controller.dto.TopPlaceListResponseDto
import com.kakaobank.place.controller.dto.toPlaceListResponseDto
import com.kakaobank.place.controller.dto.toTopPlaceListResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@RestController
class PlaceController(
    private val placeApplicationService: PlaceApplicationService
) {

    @GetMapping("/api/v1/places")
    fun searchPlace(@RequestParam query: String): ResponseEntity<PlaceListResponseDto> {
        return ResponseEntity.ok(placeApplicationService.searchPlace(query).toPlaceListResponseDto())
    }

    @GetMapping("/api/v1/top-place")
    fun searchTopPlace(
        @RequestParam(required = false, defaultValue = "10") @Min(PAGING_MIN) @Max(PAGING_MAX) size: Int
    ): ResponseEntity<TopPlaceListResponseDto> {
        return ResponseEntity.ok(placeApplicationService.searchTopPlace(size).toTopPlaceListResponseDto())
    }

    companion object {
        private const val PAGING_MIN = 1L
        private const val PAGING_MAX = 50L
    }
}
