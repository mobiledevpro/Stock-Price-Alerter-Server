package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.cryptocoinlist.local.CryptoCoin

interface CryptoCoinDAO {
    suspend fun selectAll(): List<CryptoCoin>
    suspend fun selectBy(searchSymbol: String): List<CryptoCoin>
    suspend fun add(list: List<CryptoCoin>): Boolean
}