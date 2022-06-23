package com.mobiledevpro.feature.crypto.watchlist.local.model

data class CryptoWatchlistTicker(
    val symbol: String,
    val lastPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercent: Double = 0.0,
    val updateTime: Long = 0L
)