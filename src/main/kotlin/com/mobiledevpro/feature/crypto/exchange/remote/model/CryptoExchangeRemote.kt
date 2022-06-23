package com.mobiledevpro.feature.crypto.exchange.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoExchangeRemote(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String
)