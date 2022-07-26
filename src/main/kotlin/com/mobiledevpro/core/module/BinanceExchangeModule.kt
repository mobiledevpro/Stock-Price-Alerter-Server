package com.mobiledevpro.core.module

import com.mobiledevpro.database.dao.cryptoExchangeDAO
import com.mobiledevpro.feature.crypto.exchange.toCryptoExchange
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory.getExchangeInfo
import com.mobiledevpro.network.binance.model.BinanceExchange
import com.mobiledevpro.network.binance.model.BinanceExchangeSymbol
import io.ktor.client.call.*
import io.ktor.server.application.*
import kotlinx.coroutines.*
import kotlinx.coroutines.time.delay
import java.time.Duration

fun Application.binanceExchangeInfoModule() {
    BinanceHTTPClientFactory.init(environment.config)

    launch {

        val getExchangeInfo: () -> Deferred<BinanceExchange> = {
            async(Dispatchers.IO, CoroutineStart.LAZY) {
                BinanceHTTPClientFactory.binanceHttpClient.getExchangeInfo().let { it ->
                    println("Response time: ${(it.responseTime.timestamp - it.requestTime.timestamp)} ms")

                    it.headers.entries().forEach { entry ->
                        if (entry.key.contains("X-MBX-USED-WEIGHT"))
                            println("${entry.key} : ${entry.value}")
                    }

                    val body: BinanceExchange = it.body()

                    println("RESULT BODY: symbols ${body.symbols.size}")

                    body
                }
            }
        }

        val jobCacheExchangeInfo: (BinanceExchange) -> Job = { binanceExchangeInfo ->
            launch(Dispatchers.IO, CoroutineStart.LAZY) {
                //Save to database
                binanceExchangeInfo.symbols
                    .map(BinanceExchangeSymbol::toCryptoExchange)
                    .let { coinList ->
                        cryptoExchangeDAO.add(coinList)
                    }.also { cached ->
                        println("Exchange info is ${if (cached) "cached" else "NOT cached"} to database")
                    }
            }
        }

        while (true) {
            println("---------START---------")
            try {
                println("Getting Exchange info")
                getExchangeInfo().await()
                    .also { binanceExchangeInfo ->
                        jobCacheExchangeInfo(binanceExchangeInfo).start()
                    }

            } catch (e: Exception) {
                println("EXCEPTION: ${e.localizedMessage}")
            }

            println("----------END----------")
            println("Next update in 1 hour")
            delay(Duration.ofHours(1))
        }
    }
}