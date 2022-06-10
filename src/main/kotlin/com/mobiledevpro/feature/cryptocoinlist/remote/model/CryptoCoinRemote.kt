package com.mobiledevpro.feature.cryptocoinlist.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoCoinRemote(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String
)