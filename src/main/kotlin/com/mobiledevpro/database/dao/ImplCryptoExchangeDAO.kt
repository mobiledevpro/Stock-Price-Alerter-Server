package com.mobiledevpro.database.dao

import com.mobiledevpro.database.DatabaseFactory.dbQuery
import com.mobiledevpro.database.model.CryptoExchangeTable
import com.mobiledevpro.feature.crypto.exchange.local.CryptoExchange

class ImplCryptoExchangeDAO : CryptoExchangeDAO {
    override suspend fun selectAll(): List<CryptoExchange> = dbQuery {
        CryptoExchangeTable.select()
    }

    override suspend fun searchBy(searchSymbol: String): List<CryptoExchange> = dbQuery {
        CryptoExchangeTable.selectWhereLike(searchSymbol)
    }

    override suspend fun selectBy(symbol: String): List<CryptoExchange> = dbQuery {
        CryptoExchangeTable.selectWhere(symbol)
    }

    override suspend fun add(list: List<CryptoExchange>): Boolean = dbQuery {
        if (list.isNotEmpty()) {
            CryptoExchangeTable.delete()
            for (coin in list)
                CryptoExchangeTable.insert(coin)

            true
        } else
            false
    }

}

//TODO: integrate DI and move this line to DI
val cryptoExchangeDAO: CryptoExchangeDAO = ImplCryptoExchangeDAO()