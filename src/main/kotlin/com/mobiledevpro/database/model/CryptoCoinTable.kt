package com.mobiledevpro.database.model

import com.mobiledevpro.feature.cryptocoin.local.CryptoCoin
import com.mobiledevpro.feature.cryptocoin.toCryptoCoin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

/**
 * Table to store crypto coin exchange info
 */
object CryptoCoinTable : Table("crypto_coin") {
    val symbol = varchar("symbol", 20)
    val baseAsset = varchar("base_asset", 10)
    val quoteAsset = varchar("quote_asset", 10)
    val pricePrecision = integer("price_precision")
    val quantityPrecision = integer("quantity_precision")

    override val primaryKey = PrimaryKey(symbol)

    fun insert(coin: CryptoCoin): InsertStatement<Number> =
        CryptoCoinTable.insert { t ->
            t[symbol] = coin.symbol
            t[baseAsset] = coin.baseAsset
            t[quoteAsset] = coin.quoteAsset
            t[pricePrecision] = coin.pricePrecision
            t[quantityPrecision] = coin.quantityPrecision
        }

    fun select(): List<CryptoCoin> =
        try {
            CryptoCoinTable
                .selectAll()
                .map(ResultRow::toCryptoCoin)
        } catch (e: Exception) {
            emptyList<CryptoCoin>()
        }

    fun selectWhereLike(searchSymbol: String): List<CryptoCoin> =
        try {
            CryptoCoinTable
                .select { symbol.lowerCase() like "%${searchSymbol.lowercase()}%" }
                .map(ResultRow::toCryptoCoin)
        } catch (e: Exception) {
            emptyList<CryptoCoin>()
        }

    fun selectWhere(symbol: String): List<CryptoCoin> =
        try {
            CryptoCoinTable
                .select { CryptoCoinTable.symbol.lowerCase() eq symbol.lowercase() }
                .map(ResultRow::toCryptoCoin)
        } catch (e: Exception) {
            emptyList<CryptoCoin>()
        }

    fun delete(): Int =
        CryptoCoinTable.deleteAll()
}