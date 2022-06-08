package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.cryptocoin.local.CryptoCoin

interface CryptoCoinDAO {
    suspend fun selectAll(): List<CryptoCoin>
    suspend fun select(searchSymbol: String): List<CryptoCoin>
    suspend fun add(list: List<CryptoCoin>): Boolean
}