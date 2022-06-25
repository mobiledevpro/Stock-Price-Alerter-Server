package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.crypto.exchange.local.CryptoExchange

interface CryptoExchangeDAO {
    suspend fun selectAll(): List<CryptoExchange>
    suspend fun searchBy(searchSymbol: String): List<CryptoExchange>
    suspend fun selectBy(symbol: String): List<CryptoExchange>
    suspend fun add(list: List<CryptoExchange>): Boolean
}