package com.mobiledevpro.database

import com.mobiledevpro.database.model.CustomerTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://localhost:5432/postgres"
        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = "stockuser",
            password = "securestock@wd"
        )

        transaction(database) {
            SchemaUtils.create(CustomerTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}