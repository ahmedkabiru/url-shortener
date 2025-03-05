package com.example.url_shortener.scheduler

import com.example.url_shortener.entity.UrlRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UrlCleanupService(val urlRepository: UrlRepository) {

    @Scheduled(fixedRate = 86400000) // Run every 24 hours
    fun cleanup() {
        val now= Instant.now()
        val expiredUrls = urlRepository.findByExpirationDateBefore(now)
        expiredUrls.forEach {
            evictCache(it.shortCode)
            urlRepository.delete(it)
        }
    }

    @CacheEvict("urls", key = "#shortCode")
    fun evictCache(shortCode: String?) {}

}