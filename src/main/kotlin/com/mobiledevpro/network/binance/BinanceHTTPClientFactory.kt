package com.mobiledevpro.network.binance

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import kotlinx.serialization.json.Json

object BinanceHTTPClientFactory {
    lateinit var binanceHttpClient: HttpClient

    fun init(config: ApplicationConfig) {
        binanceHttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 10000
                socketTimeoutMillis = 10000

            }
            install(HttpRequestRetry) {
                maxRetries = 5
                retryIf { _, response ->
                    !response.status.isSuccess()
                }
                delayMillis { retry ->
                    retry * 3000L
                }
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "testnet.binancefuture.com"
                    path("fapi/v1/")
                }

                header("X-MBX-APIKEY", config.property("ktor.jwt.binancePublicKey").getString())
            }
        }
    }

    suspend fun HttpClient.getExchangeInfo(): HttpResponse =
        get("exchangeInfo")

}