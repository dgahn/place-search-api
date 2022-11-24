package com.kakaopay.honey.exception

import org.springframework.validation.FieldError

data class FiledErrorDto(
    val field: String,
    val rejectedValue: String?,
    val message: String?
)

fun List<FieldError>.toFiledErrorDto(): List<FiledErrorDto> = this.map {
    FiledErrorDto(
        field = it.field,
        rejectedValue = it.rejectedValue?.toString(),
        message = it.defaultMessage?.toString()
    )
}
