package com.mobiledevpro.database.model

import com.mobiledevpro.feature.crypto.exchange.local.CryptoExchange
import com.mobiledevpro.feature.crypto.exchange.toCryptoExchange
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

/**
 * Table to store crypto exchange info from Binance API
 */
object CryptoExchangeTable : Table("crypto_exchange") {
    val symbol = varchar("symbol", 20)
    val baseAsset = varchar("base_asset", 10)
    val quoteAsset = varchar("quote_asset", 10)
    val pricePrecision = integer("price_precision")
    val quantityPrecision = integer("quantity_precision")

    override val primaryKey = PrimaryKey(symbol)

    fun insert(coin: CryptoExchange): InsertStatement<Number> =
        CryptoExchangeTable.insert { t ->
            t[symbol] = coin.symbol
            t[baseAsset] = coin.baseAsset
            t[quoteAsset] = coin.quoteAsset
            t[pricePrecision] = coin.pricePrecision
            t[quantityPrecision] = coin.quantityPrecision
        }

    fun select(): List<CryptoExchange> =
        try {
            CryptoExchangeTable
                .selectAll()
                .map(ResultRow::toCryptoExchange)
        } catch (e: Exception) {
            emptyList<CryptoExchange>()
        }

    fun selectWhereLike(searchSymbol: String): List<CryptoExchange> =
        try {
            CryptoExchangeTable
                .select { symbol.lowerCase() like "%${searchSymbol.lowercase()}%" }
                .map(ResultRow::toCryptoExchange)
        } catch (e: Exception) {
            emptyList<CryptoExchange>()
        }

    fun selectWhere(symbol: String): List<CryptoExchange> =
        try {
            CryptoExchangeTable
                .select { CryptoExchangeTable.symbol.lowerCase() eq symbol.lowercase() }
                .map(ResultRow::toCryptoExchange)
        } catch (e: Exception) {
            emptyList<CryptoExchange>()
        }

    fun delete(): Int =
        CryptoExchangeTable.deleteAll()
}