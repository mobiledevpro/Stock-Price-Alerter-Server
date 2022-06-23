package com.mobiledevpro.database.model

import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.update

/**
 * Common watchlist to subscribe on Binance Socket and get price changes
 */
object CryptoWatchlistTable : Table("crypto_watchlist") {
    val symbol = varchar("symbol", 20)
    val lastPrice = double("last_price")
    val priceChange = double("price_change")
    val priceChangePercent = double("price_change_percent")
    val updateTime = long("update_time")

    override val primaryKey = PrimaryKey(symbol)

    fun insert(ticker: CryptoWatchlistTicker): InsertStatement<Number> =
        CryptoWatchlistTable.insert { t ->
            t[symbol] = ticker.symbol
            t[lastPrice] = ticker.lastPrice
            t[priceChange] = ticker.priceChange
            t[priceChangePercent] = ticker.priceChangePercent
            t[updateTime] = ticker.updateTime
        }

    fun update(ticker: CryptoWatchlistTicker): Int =
        CryptoWatchlistTable.update(
            { (symbol eq ticker.symbol) }
        ) { t ->
            t[lastPrice] = ticker.lastPrice
            t[priceChange] = ticker.priceChange
            t[priceChangePercent] = ticker.priceChangePercent
            t[updateTime] = ticker.updateTime
        }
    /*
    fun isExist(ticker: CryptoUserWatchlistTicker): Boolean =
        try {
            CryptoWatchlistTable
                .select { (CryptoWatchlistTable.userId eq ticker.userUid) and (CryptoWatchlistTable.symbol eq ticker.symbol) }
                .map(ResultRow::toCryptoUserWatchlistTicker)
                .isNotEmpty()

        } catch (e: Exception) {
            false
        }

    fun deleteBy(ticker: CryptoUserWatchlistTicker): Int =
        CryptoWatchlistTable.deleteWhere {
            (CryptoWatchlistTable.userId eq ticker.userUid) and (CryptoWatchlistTable.symbol eq ticker.symbol)
        }

     */
}