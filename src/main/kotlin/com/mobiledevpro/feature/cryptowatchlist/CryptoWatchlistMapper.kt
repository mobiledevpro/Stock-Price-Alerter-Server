package com.mobiledevpro.feature.cryptowatchlist

import com.mobiledevpro.database.model.CryptoWatchlistTable
import com.mobiledevpro.feature.cryptowatchlist.local.CryptoWatchlistTicker
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCryptoWatchlistTicker(): CryptoWatchlistTicker =
    CryptoWatchlistTicker(
        userUid = this[CryptoWatchlistTable.userId],
        symbol = this[CryptoWatchlistTable.symbol],
        lastPrice = this[CryptoWatchlistTable.lastPrice],
        priceChange = this[CryptoWatchlistTable.priceChange],
        priceChangePercent = this[CryptoWatchlistTable.priceChangePercent],
        updateTime = this[CryptoWatchlistTable.updateTime]
    )
