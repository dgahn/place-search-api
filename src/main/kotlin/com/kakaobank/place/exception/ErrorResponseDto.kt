package com.kakaobank.place.exception

import com.kakaobank.place.util.toDateTimeString
import java.time.LocalDateTime

data class ErrorResponseDto(
    val message: String,
    val trace: String,
    val timestamp: String = LocalDateTime.now().toDateTimeString()
) {
    companion object {
        fun of(e: Exception) = ErrorResponseDto(
            message = e.message.orEmpty(),
            trace = e.stackTraceToString()
        )

        fun of(e: Exception, message: String) = ErrorResponseDto(
            message = message,
            trace = e.stackTraceToString()
        )
    }
}
