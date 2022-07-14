package com.mobiledevpro.database.model

import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker
import com.mobiledevpro.feature.crypto.userwatchlist.toCryptoUserWatchlistTicker
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

/**
 * Store watchlist for user
 */
object CryptoUserWatchlistTable : Table("crypto_user_watchlist") {
    val userId = varchar("user_uid", 40)
    val symbol = varchar("symbol", 20)

    override val primaryKey = PrimaryKey(userId, symbol)

    fun insert(ticker: CryptoUserWatchlistTicker): InsertStatement<Number> =
        CryptoUserWatchlistTable.insert { t ->
            t[userId] = ticker.userUid //for testing 20fb4092-df68-4054-8d53-698b95c64ca1
            t[symbol] = ticker.symbol
        }

    fun selectBy(userId: String): List<CryptoUserWatchlistTicker> =
        try {
            Join(
                CryptoUserWatchlistTable, CryptoWatchlistTable,
                joinType = JoinType.INNER,
                onColumn = CryptoUserWatchlistTable.symbol,
                otherColumn = CryptoWatchlistTable.symbol,
                additionalConstraint = { CryptoUserWatchlistTable.userId eq userId }
            )
                .selectAll()
                .map(ResultRow::toCryptoUserWatchlistTicker)

        } catch (e: Exception) {
            emptyList<CryptoUserWatchlistTicker>()
        }

    fun isExist(ticker: CryptoUserWatchlistTicker): Boolean =
        try {
            CryptoUserWatchlistTable
                .select { (userId eq ticker.userUid) and (symbol eq ticker.symbol) }
                .map(ResultRow::toCryptoUserWatchlistTicker)
                .isNotEmpty()

        } catch (e: Exception) {
            false
        }

    /**
     * Check is symbol exist for any user
     */
    fun isExist(symbol: String): Boolean =
        try {
            CryptoUserWatchlistTable
                .select { CryptoUserWatchlistTable.symbol eq symbol }
                .groupBy { CryptoUserWatchlistTable.symbol }.isNotEmpty()

        } catch (e: Exception) {
            false
        }

    fun deleteBy(ticker: CryptoUserWatchlistTicker): Int =
        CryptoUserWatchlistTable.deleteWhere {
            (userId eq ticker.userUid) and (symbol eq ticker.symbol)
        }
}