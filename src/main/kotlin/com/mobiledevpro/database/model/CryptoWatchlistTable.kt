package com.mobiledevpro.database.model

import com.mobiledevpro.feature.crypto.watchlist.local.model.CryptoWatchlistTicker
import com.mobiledevpro.feature.crypto.watchlist.toCryptoWatchlistTicker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

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

    fun select(): List<CryptoWatchlistTicker> =
        try {
            this.selectAll()
                .map(ResultRow::toCryptoWatchlistTicker)

        } catch (e: Exception) {
            emptyList<CryptoWatchlistTicker>()
        }

    fun insert(ticker: CryptoWatchlistTicker): InsertStatement<Number> =
        this.insert { t ->
            t[symbol] = ticker.symbol
            t[lastPrice] = ticker.lastPrice
            t[priceChange] = ticker.priceChange
            t[priceChangePercent] = ticker.priceChangePercent
            t[updateTime] = ticker.updateTime
        }

    fun update(ticker: CryptoWatchlistTicker): Int =
        this.update(
            { (symbol eq ticker.symbol) }
        ) { t ->
            t[lastPrice] = ticker.lastPrice
            t[priceChange] = ticker.priceChange
            t[priceChangePercent] = ticker.priceChangePercent
            t[updateTime] = ticker.updateTime
        }

    fun isExist(ticker: CryptoWatchlistTicker): Boolean =
        try {
            this.select { symbol eq ticker.symbol }
                .map(ResultRow::toCryptoWatchlistTicker)
                .isNotEmpty()

        } catch (e: Exception) {
            false
        }

    fun deleteBy(ticker: CryptoWatchlistTicker): Int =
        this.deleteWhere { symbol eq ticker.symbol }
}