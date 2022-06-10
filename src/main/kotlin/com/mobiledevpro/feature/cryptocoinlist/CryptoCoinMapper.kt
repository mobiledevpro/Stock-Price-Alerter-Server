package com.mobiledevpro.feature.cryptocoinlist

import com.mobiledevpro.database.model.CryptoCoinTable
import com.mobiledevpro.feature.cryptocoinlist.local.CryptoCoin
import com.mobiledevpro.feature.cryptocoinlist.remote.model.CryptoCoinRemote
import com.mobiledevpro.network.binance.model.BinanceExchangeSymbol
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCryptoCoin(): CryptoCoin =
    CryptoCoin(
        symbol = this[CryptoCoinTable.symbol],
        baseAsset = this[CryptoCoinTable.baseAsset],
        quoteAsset = this[CryptoCoinTable.quoteAsset],
        pricePrecision = this[CryptoCoinTable.pricePrecision],
        quantityPrecision = this[CryptoCoinTable.quantityPrecision]
    )

fun BinanceExchangeSymbol.toCryptoCoin(): CryptoCoin =
    CryptoCoin(
        symbol, baseAsset, quoteAsset, pricePrecision, quantityPrecision
    )

fun CryptoCoin.toRemote(): CryptoCoinRemote =
    CryptoCoinRemote(
        symbol, baseAsset, quoteAsset
    )
