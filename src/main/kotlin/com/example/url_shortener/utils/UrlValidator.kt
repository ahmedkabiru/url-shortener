package com.example.url_shortener.utils

import java.net.URI
import java.net.URISyntaxException

object UrlValidator {

    fun normalizeUrl(url : String): String {
        return if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "https://$url"
        } else {
            url
        }
    }

    fun isValidUrl(url: String): Boolean {
        return try {
            URI(url).toURL()
            true
        } catch (e: URISyntaxException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}