package com.mobiledevpro.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.config.*

object HttpClientFactory {
    suspend fun init(config: ApplicationConfig) {
        val client = HttpClient(CIO)

        val response: HttpResponse = client.get("https://ktor.io/")
        println(response.status)
        client.close()
    }
}