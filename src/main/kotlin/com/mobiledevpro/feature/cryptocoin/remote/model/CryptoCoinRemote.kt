package com.mobiledevpro.feature.cryptocoin.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoCoinRemote(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String
)