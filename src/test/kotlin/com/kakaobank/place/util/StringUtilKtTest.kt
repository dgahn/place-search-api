package com.kakaobank.place.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StringUtilKtTest {
    @Test
    fun `두_문장의_유사도를_구할_수_있다`() {
        "대전 서구 탄방동 659".similarity("대전광역시 서구 탄방동 659") shouldBe 0.8125
        "대전 서구 문정로 56".similarity("대전광역시 서구 문정로 56") shouldBe 0.8
    }
}
