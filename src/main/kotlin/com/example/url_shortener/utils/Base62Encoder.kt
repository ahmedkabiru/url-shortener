package com.example.url_shortener.utils

object Base62Encoder {


    private const val BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val base62Chars = BASE62.toCharArray()

    fun encode(number: Long): String {
        if (number == 0L) return base62Chars[0].toString()
        val sb = StringBuilder()
        var num = number
        while (num > 0) {
            sb.append(base62Chars[(num % 62).toInt()])
            num /= 62
        }
        return sb.reverse().toString()
    }
}