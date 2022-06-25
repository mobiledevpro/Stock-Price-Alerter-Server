package com.mobiledevpro.feature.crypto.userwatchlist

import com.mobiledevpro.database.model.CryptoUserWatchlistTable
import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker
import com.mobiledevpro.feature.crypto.userwatchlist.remote.model.CryptoUserWatchlistRemote
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCryptoUserWatchlistTicker(): CryptoUserWatchlistTicker =
    CryptoUserWatchlistTicker(
        userUid = this[CryptoUserWatchlistTable.userId],
        symbol = this[CryptoUserWatchlistTable.symbol],
    )


fun CryptoUserWatchlistTicker.toRemote(): CryptoUserWatchlistRemote =
    CryptoUserWatchlistRemote(
        symbol
    )