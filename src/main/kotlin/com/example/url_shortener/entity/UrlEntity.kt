package com.example.url_shortener.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant


@Entity
@Table(name = "urls")
class UrlEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val shortCode:String? = null,

    val longUrl:String? = null,

    val expirationDate: Instant?= null,

    @CreationTimestamp
    val createdDate: Instant?= null
)