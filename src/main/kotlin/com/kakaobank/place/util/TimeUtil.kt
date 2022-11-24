package com.kakaobank.place.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val KST: ZoneId = ZoneId.of("Asia/Seoul")
private const val DATE_TIME_PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss"

fun LocalDateTime.toDateTimeString(zoneId: ZoneId = KST): String {
    val formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_FORMAT)
        .withZone(zoneId)
    return formatter.format(this)
}
