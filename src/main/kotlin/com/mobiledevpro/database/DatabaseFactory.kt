package com.mobiledevpro.database

import com.mobiledevpro.database.model.CryptoExchangeTable
import com.mobiledevpro.database.model.CryptoUserWatchlistTable
import com.mobiledevpro.database.model.CustomerTable
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.storage.driverClassName").getString()
        val jdbcURL = config.property("ktor.storage.jdbcURL").getString()
        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName
        )

        transaction(database) {
            SchemaUtils.create(CustomerTable)
            SchemaUtils.create(CryptoExchangeTable)
            SchemaUtils.create(CryptoUserWatchlistTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
}