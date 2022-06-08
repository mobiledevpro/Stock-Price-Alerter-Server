package com.mobiledevpro.network.binance.model

import kotlinx.serialization.Serializable

@Serializable
data class BinanceExchange(
    val symbols: List<BinanceExchangeSymbol>
)

@Serializable
data class BinanceExchangeSymbol(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val pricePrecision: Int,
    val quantityPrecision: Int
)