package com.mobiledevpro.feature.crypto.watchlist.repository

import com.mobiledevpro.database.dao.cryptoWatchlistDAO
import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import com.mobiledevpro.network.binance.model.BinanceSocket

class ImplCryptoWatchlistRepository : CryptoWatchlistRepository {

    override suspend fun getTickerListLocal(): List<CryptoWatchlistTicker> =
        cryptoWatchlistDAO.select()

    override suspend fun createTickerRequestRemote(
        method: BinanceSocket.Method,
        tickerList: List<CryptoWatchlistTicker>
    ): BinanceSocket.Request {
        val params: Array<String> = tickerList.mapTo(ArrayList<String>()) { ticker ->
            "${ticker.symbol.lowercase()}@ticker"
        }.toTypedArray()

        return BinanceSocket.Request(
            method = method,
            params = params,
            1
        )
    }
}