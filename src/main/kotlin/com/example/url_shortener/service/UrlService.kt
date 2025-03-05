package com.example.url_shortener.service

import com.example.url_shortener.dtos.UrlRequestDto
import com.example.url_shortener.entity.UrlEntity
import com.example.url_shortener.entity.UrlRepository
import com.example.url_shortener.exception.NotFoundException
import com.example.url_shortener.utils.Base62Encoder
import com.example.url_shortener.utils.UrlValidator
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UrlService(
    private val urlRepository: UrlRepository,
    private val redisCounterService: RedisCounterService
) {

    fun createShortUrl(urlRequestDto: UrlRequestDto): String? {
        val shortCode = generateUniqueShortCode()
        val urlEntity = urlRepository.save(UrlEntity(shortCode=shortCode, longUrl = urlRequestDto.longUrl, expirationDate = urlRequestDto.expirationDate))
        return urlEntity.shortCode
    }

    @Cacheable("urls", key = "#shortCode")
    fun getLongUrlByShortCode(shortCode: String): String? {
        val urlEntity=  urlRepository.findByShortCode(shortCode) ?: throw NotFoundException("short code $shortCode can not found")
        return urlEntity.longUrl
    }

    private fun generateUniqueShortCode(): String{
        val counter = redisCounterService.getNextCounter()
        return  Base62Encoder.encode(counter)
    }

}