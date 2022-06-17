package com.mobiledevpro.database.dao

interface CryptoWatchlistDAO {
    suspend fun add(userUid: String, symbol: String): Boolean
    suspend fun delete(userUid: String, symbol: String): Boolean
    suspend fun isExist(userUid: String, symbol: String): Boolean
}