package com.example.url_shortener.service

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisCounterService(private val redisTemplate: StringRedisTemplate) {

    companion object{
        private const val COUNTER_KEY = "url_shortener_counter"
    }

    fun getNextCounter(): Long{
        return  redisTemplate.opsForValue().increment(COUNTER_KEY) ?: throw IllegalStateException("Failed to increment counter")
    }

}