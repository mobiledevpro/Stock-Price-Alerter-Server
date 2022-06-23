package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker

interface CryptoWatchlistDAO {

    suspend fun select(userUid: String): List<CryptoUserWatchlistTicker>
    suspend fun add(userUid: String, symbol: String): Boolean
    suspend fun delete(userUid: String, symbol: String): Boolean
    suspend fun isExist(userUid: String, symbol: String): Boolean
}