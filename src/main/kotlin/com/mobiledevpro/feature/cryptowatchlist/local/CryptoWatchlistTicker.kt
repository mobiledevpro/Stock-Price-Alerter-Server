package com.mobiledevpro.feature.cryptowatchlist.local

data class CryptoWatchlistTicker(
    val userUid: String,
    val symbol: String,
    val lastPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercent: Double = 0.0,
    val updateTime: Long = 0L
)