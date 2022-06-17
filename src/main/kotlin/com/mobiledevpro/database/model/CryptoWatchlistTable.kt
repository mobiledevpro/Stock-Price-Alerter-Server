package com.mobiledevpro.database.model

import com.mobiledevpro.feature.cryptowatchlist.local.CryptoWatchlistTicker
import com.mobiledevpro.feature.cryptowatchlist.toCryptoWatchlistTicker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

/**
 * Store watchlist for user
 */
object CryptoWatchlistTable : Table("crypto_watchlist") {
    val userId = varchar("user_uid", 40)
    val symbol = varchar("symbol", 20)
    val lastPrice = double("last_price")
    val priceChange = double("price_change")
    val priceChangePercent = double("price_change_percent")
    val updateTime = long("update_time")

    override val primaryKey = PrimaryKey(userId, symbol)

    fun insert(ticker: CryptoWatchlistTicker): InsertStatement<Number> =
        CryptoWatchlistTable.insert { t ->
            t[userId] = ticker.userUid //for testing 20fb4092-df68-4054-8d53-698b95c64ca1
            t[symbol] = ticker.symbol
            t[lastPrice] = ticker.lastPrice
            t[priceChange] = ticker.priceChange
            t[priceChangePercent] = ticker.priceChangePercent
            t[updateTime] = ticker.updateTime
        }

    fun update(ticker: CryptoWatchlistTicker): Int =
        CryptoWatchlistTable.update(
            { (userId eq ticker.userUid) and (symbol eq ticker.symbol) }
        ) { t ->
            t[lastPrice] = ticker.lastPrice
            t[priceChange] = ticker.priceChange
            t[priceChangePercent] = ticker.priceChangePercent
            t[updateTime] = ticker.updateTime
        }

    fun selectBy(userId: String): List<CryptoWatchlistTicker> =
        try {
            CryptoWatchlistTable
                .select { CryptoWatchlistTable.userId eq userId }
                .map(ResultRow::toCryptoWatchlistTicker)

        } catch (e: Exception) {
            emptyList<CryptoWatchlistTicker>()
        }

    fun isExist(ticker: CryptoWatchlistTicker): Boolean =
        try {
            CryptoWatchlistTable
                .select { (CryptoWatchlistTable.userId eq ticker.userUid) and (CryptoWatchlistTable.symbol eq ticker.symbol) }
                .map(ResultRow::toCryptoWatchlistTicker)
                .isNotEmpty()

        } catch (e: Exception) {
            false
        }

    fun deleteBy(ticker: CryptoWatchlistTicker): Int =
        CryptoWatchlistTable.deleteWhere {
            (CryptoWatchlistTable.userId eq ticker.userUid) and (CryptoWatchlistTable.symbol eq ticker.symbol)
        }
}