package com.mobiledevpro.feature.crypto.exchange.local

data class CryptoExchange(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val pricePrecision: Int,
    val quantityPrecision: Int
)