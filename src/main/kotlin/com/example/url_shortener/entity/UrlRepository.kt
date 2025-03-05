package com.example.url_shortener.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : JpaRepository<UrlEntity, Long> {

    fun findByShortCode(shortCode: String): UrlEntity?
}