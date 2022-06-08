package com.mobiledevpro

import com.mobiledevpro.core.models.Version
import com.mobiledevpro.core.plugins.configureHTTP
import com.mobiledevpro.core.plugins.configureRouting
import com.mobiledevpro.core.plugins.configureSerialization
import com.mobiledevpro.database.DatabaseFactory
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory.binanceHttpClient
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory.getExchangeInfo
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSerialization()
    configureHTTP()
}

fun Application.moduleV1() {
    configureRouting(Version.V1)

    DatabaseFactory.init(environment.config)
    BinanceHTTPClientFactory.init(environment.config)

    launch {
        while (true) {
            println("-----------------")
            println("Get Exchange info")
            binanceHttpClient.getExchangeInfo().let {
                println("Response time: ${(it.responseTime.timestamp - it.requestTime.timestamp)} ms")

                it.headers.entries().forEach { entry ->
                    if (entry.key.contains("X-MBX-USED-WEIGHT"))
                        println("${entry.key} : ${entry.value}")
                }
            }
            delay(Duration.ofHours(1))
        }
    }
}