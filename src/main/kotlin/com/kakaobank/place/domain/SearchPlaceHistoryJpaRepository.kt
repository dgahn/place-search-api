package com.kakaobank.place.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SearchPlaceHistoryJpaRepository : JpaRepository<SearchPlaceHistory, Long> {
    fun findByKeyword(keyword: String): SearchPlaceHistory?
}
