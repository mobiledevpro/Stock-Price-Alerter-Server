package com.mobiledevpro.feature.cryptocoin

import com.mobiledevpro.database.model.CryptoCoinTable
import com.mobiledevpro.feature.cryptocoin.local.CryptoCoin
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCryptoCoin(): CryptoCoin =
    CryptoCoin(
        symbol = this[CryptoCoinTable.symbol],
        baseAsset = this[CryptoCoinTable.baseAsset],
        quoteAsset = this[CryptoCoinTable.quoteAsset],
        pricePrecision = this[CryptoCoinTable.pricePrecision],
        quantityPrecision = this[CryptoCoinTable.quantityPrecision]
    )