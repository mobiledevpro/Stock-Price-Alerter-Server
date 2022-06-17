package com.mobiledevpro.database.dao

import com.mobiledevpro.database.DatabaseFactory.dbQuery
import com.mobiledevpro.database.model.CryptoCoinTable
import com.mobiledevpro.feature.cryptocoin.local.CryptoCoin

class ImplCryptoCoinDAO : CryptoCoinDAO {
    override suspend fun selectAll(): List<CryptoCoin> = dbQuery {
        CryptoCoinTable.select()
    }

    override suspend fun searchBy(searchSymbol: String): List<CryptoCoin> = dbQuery {
        CryptoCoinTable.selectWhereLike(searchSymbol)
    }

    override suspend fun selectBy(symbol: String): List<CryptoCoin> = dbQuery {
        CryptoCoinTable.selectWhere(symbol)
    }

    override suspend fun add(list: List<CryptoCoin>): Boolean = dbQuery {
        if (list.isNotEmpty()) {
            CryptoCoinTable.delete()
            for (coin in list)
                CryptoCoinTable.insert(coin)
        }

        false
    }

}

//TODO: integrate DI and move this line to DI
val cryptoCoinDAO: CryptoCoinDAO = ImplCryptoCoinDAO()