package com.mobiledevpro.database.dao

import com.mobiledevpro.database.DatabaseFactory.dbQuery
import com.mobiledevpro.database.model.CryptoWatchlistTable
import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker

class ImplCryptoWatchlistDAO : CryptoWatchlistDAO {

    override suspend fun select(): List<CryptoWatchlistTicker> = dbQuery {
        CryptoWatchlistTable.select()
    }

    override suspend fun add(symbol: String): Boolean = dbQuery {
        CryptoWatchlistTicker(
            symbol
        ).let(CryptoWatchlistTable::insert)

        true
    }

    override suspend fun delete(symbol: String): Boolean = dbQuery {
        CryptoWatchlistTicker(
            symbol
        )
            .let(CryptoWatchlistTable::deleteBy)
            .let {
                it > 0
            }
    }

    override suspend fun isExist(symbol: String): Boolean = dbQuery {
        CryptoWatchlistTicker(
            symbol
        ).let(CryptoWatchlistTable::isExist)
    }
}

//TODO: integrate DI and move this line to DI
val cryptoWatchlistDAO: CryptoWatchlistDAO = ImplCryptoWatchlistDAO()
