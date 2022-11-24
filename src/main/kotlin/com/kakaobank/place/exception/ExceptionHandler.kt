package com.kakaobank.place.exception

import com.kakaopay.honey.exception.ConstraintErrorDto
import com.kakaopay.honey.exception.toFiledErrorDto
import com.kakaopay.honey.util.toJson
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponseDto> {
        logger.error { e.message }
        return ResponseEntity(ErrorResponseDto.of(e), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponseDto> {
        logger.error { e.message }
        return ResponseEntity(ErrorResponseDto.of(e), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ErrorResponseDto> {
        val message = e.constraintViolations
            .map {
                ConstraintErrorDto(
                    it.propertyPath.toString().split(CONSTRAINT_VIOLATION_EXCEPTION_PATH_DELIMITER).last(),
                    it.invalidValue.toString(),
                    it.message
                )
            }
            .toJson()
        logger.error { e.message }
        return ResponseEntity(ErrorResponseDto.of(e, message), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponseDto> {
        val message = e.bindingResult.fieldErrors.toFiledErrorDto().toJson()
        logger.error { e.message }
        return ResponseEntity(ErrorResponseDto.of(e, message), HttpStatus.BAD_REQUEST)
    }

    companion object {
        private val logger = KotlinLogging.logger { }

        private const val CONSTRAINT_VIOLATION_EXCEPTION_PATH_DELIMITER = "."
    }
}
