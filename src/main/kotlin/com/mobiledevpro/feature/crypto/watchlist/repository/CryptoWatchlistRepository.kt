package com.mobiledevpro.feature.crypto.watchlist.repository

import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import com.mobiledevpro.network.binance.model.BinanceSocket

interface CryptoWatchlistRepository {

    suspend fun getTickerListLocal(): List<CryptoWatchlistTicker>

    suspend fun createTickerRequestRemote(
        method: BinanceSocket.Method,
        tickerList: List<CryptoWatchlistTicker>
    ): BinanceSocket.Request
}

val cryptoWatchlistRepository = ImplCryptoWatchlistRepository()