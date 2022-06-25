package com.mobiledevpro.core.module

import com.mobiledevpro.core.models.watchlistInsertDeleteChannel
import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import com.mobiledevpro.feature.crypto.watchlist.repository.cryptoWatchlistRepository
import com.mobiledevpro.network.binance.BinanceSocketClientFactory
import com.mobiledevpro.network.binance.BinanceSocketClientFactory.subscribe
import com.mobiledevpro.network.binance.BinanceSocketClientFactory.unsubscribe
import com.mobiledevpro.network.binance.model.BinanceSocket
import io.ktor.server.application.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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
                            // coroutineContext.ensureActive()
                            println(textFrame?.readText())
                            //TODO: cache to table
                            jobCacheTickerPrice(tickerList).start()
                        }
                }
        }
    }

    //Job to unsubscribe from tickers
    val jobUnsubscribeTickers: (List<CryptoWatchlistTicker>) -> Job = { tickerList ->
        launch(Dispatchers.IO, CoroutineStart.LAZY) {
            cryptoWatchlistRepository.createTickerRequestRemote(BinanceSocket.Method.UNSUBSCRIBE, tickerList)
                .also { request ->
                    println("Call request ${request.method}")
                    BinanceSocketClientFactory.binanceSocketClient.unsubscribe(request)
                        .collect { textFrame: Frame.Text? ->
                            println(textFrame?.readText())
                        }
                }
        }
    }

    launch(Dispatchers.IO) {

        var jobSubscribe: Job? = null
        var jobUnsubscribe: Job? = null
        var currentTickerList: List<CryptoWatchlistTicker> = emptyList()

        cachedWatchlistFlow.collect { newTickerList: List<CryptoWatchlistTicker> ->
            println("New ticker list: ${newTickerList.size}")

            if (currentTickerList == newTickerList) return@collect

            //unsubscribe
            if (currentTickerList.isNotEmpty()) {
                jobUnsubscribe = jobUnsubscribeTickers(currentTickerList)
                jobUnsubscribe?.start()
            }

            currentTickerList = newTickerList
            if (newTickerList.isEmpty()) return@collect

            //Subscribe
            jobSubscribe?.cancel()
            jobSubscribe = jobSubscribeOnTicker(newTickerList)
            jobSubscribe?.start()
        }
    }
}