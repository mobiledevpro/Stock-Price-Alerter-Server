package com.mobiledevpro.feature.crypto.watchlist

import com.mobiledevpro.database.model.CryptoWatchlistTable
import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCryptoWatchlistTicker(): CryptoWatchlistTicker =
    CryptoWatchlistTicker(
        symbol = this[CryptoWatchlistTable.symbol],
        lastPrice = this[CryptoWatchlistTable.lastPrice],
        priceChange = this[CryptoWatchlistTable.priceChange],
        priceChangePercent = this[CryptoWatchlistTable.priceChangePercent],
        updateTime = this[CryptoWatchlistTable.updateTime]
    )