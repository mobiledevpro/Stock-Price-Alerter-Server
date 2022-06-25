package com.mobiledevpro.core.module

import com.mobiledevpro.database.dao.cryptoExchangeDAO
import com.mobiledevpro.feature.crypto.exchange.toCryptoExchange
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory
import com.mobiledevpro.network.binance.BinanceHTTPClientFactory.getExchangeInfo
import com.mobiledevpro.network.binance.model.BinanceExchange
import com.mobiledevpro.network.binance.model.BinanceExchangeSymbol
import io.ktor.client.call.*
import io.ktor.server.application.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

fun Application.binanceExchangeInfoModule() {
    BinanceHTTPClientFactory.init(environment.config)

    launch {
        while (true) {
            println("-----------------")
            val binanceExchangeInfoDeferred: Deferred<BinanceExchange> = async(Dispatchers.IO) {
                println("Getting Exchange info")
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

            try {
                val binanceExchangeInfo: BinanceExchange = binanceExchangeInfoDeferred.await()

                val cacheExchangeInfoDeferred = async(Dispatchers.IO) {
                    //Save to database
                    binanceExchangeInfo.symbols
                        .map(BinanceExchangeSymbol::toCryptoExchange)
                        .let { coinList ->
                            cryptoExchangeDAO.add(coinList)
                        }

                    true
                }

                val isExchangeInfoCached: Boolean = cacheExchangeInfoDeferred.await()
                println("Exchange info is ${if (isExchangeInfoCached) "cached" else "NOT cached"} to database")

            } catch (e: Exception) {
                println("EXCEPTION: ${e.localizedMessage}")
            }

            println("-----------------")
            println("Next update in 1 hour")
            delay(Duration.ofHours(1))
        }
    }
}