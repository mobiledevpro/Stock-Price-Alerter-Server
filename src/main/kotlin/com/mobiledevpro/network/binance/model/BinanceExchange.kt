package com.mobiledevpro.network.binance.model

import kotlinx.serialization.Serializable

@Serializable
data class BinanceExchange(
    val symbols: List<BinanceSymbol>
)

@Serializable
data class BinanceSymbol(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val pricePrecision: Int,
    val quantityPrecision: Int
)