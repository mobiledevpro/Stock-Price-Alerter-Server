package com.mobiledevpro.feature.binance

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*

object BinanceHTTPClientFactory {
    lateinit var binanceHttpClient: HttpClient

    fun init(config: ApplicationConfig) {
        binanceHttpClient = HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
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

                header("X-MBX-APIKEY", PUBLIC_KEY)
            }
        }
    }

    suspend fun HttpClient.getExchangeInfo(): HttpResponse =
        get("exchangeInfo")


    private const val PUBLIC_KEY = "a8ca86b682a36b97f7fe679d074c7e4e85495ca2f58d3c7c2870ffec77f85a4b"
    private const val SECRET_KEY = "251fc3fa22f1eb429bdeae059d624c29579cd973dd7122c3517d0a25077c7061"
}