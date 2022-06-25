package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker

interface CryptoWatchlistDAO {
    suspend fun select(): List<CryptoWatchlistTicker>
    suspend fun add(symbol: String): Boolean
    suspend fun delete(symbol: String): Boolean
    suspend fun isExist(symbol: String): Boolean
}