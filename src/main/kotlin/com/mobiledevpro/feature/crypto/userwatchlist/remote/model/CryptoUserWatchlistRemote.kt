package com.mobiledevpro.feature.crypto.userwatchlist.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoUserWatchlistRemote(
    val symbol: String,
    val lastPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercent: Double = 0.0,
    val updateTime: Long = 0L
)