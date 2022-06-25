package com.mobiledevpro.core.module

import com.mobiledevpro.core.models.watchlistInsertDeleteChannel
import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import com.mobiledevpro.feature.crypto.watchlist.repository.cryptoWatchlistRepository
import com.mobiledevpro.network.binance.BinanceSocketClientFactory
import com.mobiledevpro.network.binance.BinanceSocketClientFactory.subscribe
import com.mobiledevpro.network.binance.model.BinanceSocket
import io.ktor.server.application.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.time.delay
import java.time.Duration

fun Application.binanceTickersModule() {
    BinanceSocketClientFactory.init(environment.config)

    val cachedWatchlistFlow = flow<List<CryptoWatchlistTicker>> {

        while (true) {
            cryptoWatchlistRepository.getTickerListLocal()
                .also { list -> emit(list) }

            //receive an event watchlist was changed
            watchlistInsertDeleteChannel.receive()
            println("Receive from channel")
        }
    }

    //Job to cache ticker price to database
    val jobCacheTickerPrice: (List<CryptoWatchlistTicker>) -> Job = { tickerList ->
        launch(Dispatchers.IO, CoroutineStart.LAZY) {
            println("Cache data to database: list ${tickerList.size}")
        }
    }

    //Job to subscribe on tickers price change
    val jobSubscribeOnTicker: (List<CryptoWatchlistTicker>) -> Job = { tickerList ->
        launch(Dispatchers.IO, CoroutineStart.LAZY) {
            cryptoWatchlistRepository.createTickerRequestRemote(BinanceSocket.Method.SUBSCRIBE, tickerList)
                .also { request ->
                    delay(Duration.ofSeconds(3))
                    println("Call request ${request.method}")
                    BinanceSocketClientFactory.binanceSocketClient.subscribe(request)
                        .collect { textFrame: Frame.Text? ->
                            if (isActive)
                                println("Subscribe flow is active $isActive")
                            // coroutineContext.ensureActive()
                            println(textFrame?.readText())
                            //TODO: cache to table
                            jobCacheTickerPrice(tickerList).start()
                        }
                }
        }
    }

    launch(Dispatchers.IO) {

        var jobSubscription: Job? = null
        var currentTickerList: List<CryptoWatchlistTicker> = emptyList()

        cachedWatchlistFlow.collect { newTickerList: List<CryptoWatchlistTicker> ->
            println("New ticker list: ${newTickerList.size}")

            if (currentTickerList == newTickerList) return@collect
            currentTickerList = newTickerList

            jobSubscription?.cancel()
            if (newTickerList.isEmpty()) return@collect

            jobSubscription = jobSubscribeOnTicker(newTickerList)
            jobSubscription?.start()

        }
    }
}