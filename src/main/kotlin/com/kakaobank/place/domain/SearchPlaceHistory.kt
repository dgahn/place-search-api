package com.kakaobank.place.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class SearchPlaceHistory(
    @Id
    val keyword: String,
    approvedAt: Instant? = null,
    updatedAt: Instant? = null
) {
    @CreatedDate
    @Column(updatable = false)
    var createdAt: Instant? = approvedAt
        protected set

    @LastModifiedDate
    var updatedAt: Instant? = updatedAt
        protected set

    @Version
    var version: Long = 1
        protected set

    var count: Int = 0
        protected set

    fun countUp() {
        count += ADD_COUNT_VALUE
    }

    companion object {
        private const val ADD_COUNT_VALUE = 1
    }
}
