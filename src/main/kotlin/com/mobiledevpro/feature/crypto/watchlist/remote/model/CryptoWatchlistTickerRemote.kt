package com.mobiledevpro.feature.crypto.watchlist.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoWatchlistTickerRemote(
    @SerialName("s")
    val symbol: String,
    @SerialName("c")
    val lastPrice: Double = 0.0,
    @SerialName("p")
    val priceChange: Double = 0.0,
    @SerialName("P")
    val priceChangePercent: Double = 0.0,
    @SerialName("E")
    val updateTime: Long = 0L
)