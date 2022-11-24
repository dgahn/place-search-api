package com.kakaobank.place.domain

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SearchPlaceHistoryJpaRepository : JpaRepository<SearchPlaceHistory, String> {
    fun findAllByOrderByCountDesc(pageable: Pageable): List<SearchPlaceHistory>
}
