package com.kakaopay.honey.exception

data class ConstraintErrorDto(
    val field: String,
    val invalidValue: String,
    val errorMessage: String,
)
