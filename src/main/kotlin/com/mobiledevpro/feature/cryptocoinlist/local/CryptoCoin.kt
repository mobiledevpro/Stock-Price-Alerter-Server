package com.mobiledevpro.feature.cryptocoinlist.local

data class CryptoCoin(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val pricePrecision: Int,
    val quantityPrecision: Int
)