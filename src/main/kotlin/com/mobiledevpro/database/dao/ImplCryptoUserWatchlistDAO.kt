package com.mobiledevpro.database.dao

import com.mobiledevpro.database.DatabaseFactory.dbQuery
import com.mobiledevpro.database.model.CryptoUserWatchlistTable
import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker

class ImplCryptoUserWatchlistDAO : CryptoUserWatchlistDAO {

    override suspend fun select(userUid: String): List<CryptoUserWatchlistTicker> = dbQuery {
        CryptoUserWatchlistTable.selectBy(userUid)
    }

    override suspend fun add(userUid: String, symbol: String): Boolean = dbQuery {
        CryptoUserWatchlistTicker(
            userUid,
            symbol
        ).let(CryptoUserWatchlistTable::insert)

        true
    }

    override suspend fun delete(userUid: String, symbol: String): Boolean = dbQuery {
        CryptoUserWatchlistTicker(
            userUid,
            symbol
        )
            .let(CryptoUserWatchlistTable::deleteBy)
            .let {
                it > 0
            }
    }

    override suspend fun isExist(userUid: String, symbol: String): Boolean = dbQuery {
        CryptoUserWatchlistTicker(
            userUid,
            symbol
        ).let(CryptoUserWatchlistTable::isExist)
    }

    /**
     * Check is symbol exist for any user
     */
    override suspend fun isExist(symbol: String): Boolean = dbQuery {
        CryptoUserWatchlistTable.isExist(symbol)
    }
}

//TODO: integrate DI and move this line to DI
val cryptoUserWatchlistDAO: CryptoUserWatchlistDAO = ImplCryptoUserWatchlistDAO()
