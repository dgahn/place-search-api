package com.kakaobank.place.controller

import com.kakaobank.place.application.PlaceApplicationService
import com.kakaobank.place.controller.dto.PlaceListResponseDto
import com.kakaobank.place.controller.dto.toPlaceListResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController(
    private val placeApplicationService: PlaceApplicationService
) {

    @GetMapping("/api/v1/places")
    fun searchPlace(@RequestParam query: String): ResponseEntity<PlaceListResponseDto> {
        return ResponseEntity.ok(placeApplicationService.searchPlace(query).toPlaceListResponseDto())
    }
}
