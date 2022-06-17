package com.mobiledevpro.database.dao

import com.mobiledevpro.database.DatabaseFactory.dbQuery
import com.mobiledevpro.database.model.CryptoWatchlistTable
import com.mobiledevpro.feature.cryptowatchlist.local.CryptoWatchlistTicker

class ImplCryptoWatchlistDAO : CryptoWatchlistDAO {
    override suspend fun add(userUid: String, symbol: String): Boolean = dbQuery {
        CryptoWatchlistTicker(
            userUid,
            symbol
        ).let(CryptoWatchlistTable::insert)

        true
    }

    override suspend fun delete(userUid: String, symbol: String): Boolean = dbQuery {
        CryptoWatchlistTicker(
            userUid,
            symbol
        )
            .let(CryptoWatchlistTable::deleteBy)
            .let {
                it > 0
            }
    }

    override suspend fun isExist(userUid: String, symbol: String): Boolean = dbQuery {
        CryptoWatchlistTicker(
            userUid,
            symbol
        ).let(CryptoWatchlistTable::isExist)
    }
}

//TODO: integrate DI and move this line to DI
val cryptoWatchlistDAO: CryptoWatchlistDAO = ImplCryptoWatchlistDAO()
