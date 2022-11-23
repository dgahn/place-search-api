package com.kakaobank.place.geo

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.abs

class GeoTransTest {
    @Test
    fun `네이버_좌표계를_WGS84좌표계로 변환할 수 있다`() {
        val point = GeoTransPoint(317273.0, 545470.0)
        val geoPoint = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, point)
        (abs(geoPoint.x - 127.06201112978057) < 0.0001) shouldBe true
        (abs(geoPoint.y - 37.50768440590033) < 0.0001) shouldBe true
    }
}
