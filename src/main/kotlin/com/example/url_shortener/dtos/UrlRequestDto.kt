package com.example.url_shortener.dtos

import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class UrlRequestDto(

    @field:NotBlank(message = "longUrl is required")
    val longUrl:String,

    val expirationDate: Instant?= null,
)
