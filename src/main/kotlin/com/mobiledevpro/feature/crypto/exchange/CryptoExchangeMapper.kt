package com.mobiledevpro.feature.crypto.exchange

import com.mobiledevpro.database.model.CryptoExchangeTable
import com.mobiledevpro.feature.crypto.exchange.local.CryptoExchange
import com.mobiledevpro.feature.crypto.exchange.remote.model.CryptoExchangeRemote
import com.mobiledevpro.network.binance.model.BinanceExchangeSymbol
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCryptoExchange(): CryptoExchange =
    CryptoExchange(
        symbol = this[CryptoExchangeTable.symbol],
        baseAsset = this[CryptoExchangeTable.baseAsset],
        quoteAsset = this[CryptoExchangeTable.quoteAsset],
        pricePrecision = this[CryptoExchangeTable.pricePrecision],
        quantityPrecision = this[CryptoExchangeTable.quantityPrecision]
    )

fun BinanceExchangeSymbol.toCryptoExchange(): CryptoExchange =
    CryptoExchange(
        symbol, baseAsset, quoteAsset, pricePrecision, quantityPrecision
    )

fun CryptoExchange.toRemote(): CryptoExchangeRemote =
    CryptoExchangeRemote(
        symbol, baseAsset, quoteAsset
    )
