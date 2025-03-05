package com.example.url_shortener.service

import com.example.url_shortener.dtos.UrlRequestDto
import com.example.url_shortener.entity.UrlEntity
import com.example.url_shortener.entity.UrlRepository
import com.example.url_shortener.exception.NotFoundException
import com.example.url_shortener.utils.UrlValidator
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.absoluteValue

@Service
class UrlService(private val urlRepository: UrlRepository) {


    fun createShortUrl(urlRequestDto: UrlRequestDto): String? {
        val normalizedUrl = UrlValidator.normalizeUrl(urlRequestDto.longUrl)
        val shortCode = generateUniqueShortCode()
        val urlEntity = urlRepository.save(UrlEntity(shortCode=shortCode, longUrl = normalizedUrl, expirationDate = urlRequestDto.expirationDate))
        return urlEntity.shortCode
    }

    @Cacheable("urls", key = "#shortCode")
    fun getLongUrlByShortCode(shortCode: String): String? {
        val urlEntity=  urlRepository.findByShortCode(shortCode) ?: throw NotFoundException("short code $shortCode can not found")
        return urlEntity.longUrl
    }

    private fun generateUniqueShortCode(): String{
        val random = Random().nextLong().absoluteValue
        return Base64.getEncoder().encodeToString(random.toString().toByteArray()).take(8)
    }

}