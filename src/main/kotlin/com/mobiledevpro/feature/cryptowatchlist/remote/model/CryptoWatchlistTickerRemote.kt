package com.mobiledevpro.feature.cryptowatchlist.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoWatchlistTickerRemote(
    val symbol: String,
    val lastPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercent: Double = 0.0,
    val updateTime: Long = 0L
)