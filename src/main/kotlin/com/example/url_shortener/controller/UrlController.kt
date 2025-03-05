package com.example.url_shortener.controller

import com.example.url_shortener.dtos.UrlRequestDto
import com.example.url_shortener.dtos.UrlResponseDto
import com.example.url_shortener.exception.InvalidURLException
import com.example.url_shortener.service.UrlService
import com.example.url_shortener.utils.UrlValidator
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class UrlController(private val urlService: UrlService) {


    @PostMapping("/urls")
    fun generateShortUrl(@Valid @RequestBody urlRequestDto: UrlRequestDto): ResponseEntity<UrlResponseDto> {
        val host = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()
        if (!UrlValidator.isValidUrl(urlRequestDto.longUrl)) {
            throw InvalidURLException("The long URL provided is invalid")
        }
        val shortCode = urlService.createShortUrl(urlRequestDto)
        return ResponseEntity.ok(UrlResponseDto("$host/$shortCode"))
    }

    @GetMapping("/{short_code}")
    fun getUrl(@PathVariable("short_code") shortCode: String): ResponseEntity<String> {
        val longUrl = urlService.getLongUrlByShortCode(shortCode)
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
            .header(HttpHeaders.LOCATION, longUrl)
            .build()
    }

}