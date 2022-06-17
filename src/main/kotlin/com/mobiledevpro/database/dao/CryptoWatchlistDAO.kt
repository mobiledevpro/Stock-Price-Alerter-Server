package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.cryptowatchlist.local.CryptoWatchlistTicker

interface CryptoWatchlistDAO {

    suspend fun select(userUid: String): List<CryptoWatchlistTicker>
    suspend fun add(userUid: String, symbol: String): Boolean
    suspend fun delete(userUid: String, symbol: String): Boolean
    suspend fun isExist(userUid: String, symbol: String): Boolean
}