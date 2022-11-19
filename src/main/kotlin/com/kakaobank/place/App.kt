package com.kakaobank.place

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(arg: Array<String>) {
    runApplication<App>(*arg)
}
