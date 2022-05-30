package com.mobiledevpro.database.model

import com.mobiledevpro.feature.customer.local.model.Customer
import com.mobiledevpro.feature.customer.toCustomer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement

object CustomerTable : Table("customer") {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 20)
    val lastName = varchar("last_name", 20)

    override val primaryKey = PrimaryKey(id)

    fun insert(customer: Customer): InsertStatement<Number> =
        CustomerTable.insert {
            it[firstName] = customer.firstName
            it[lastName] = customer.lastName
        }

    fun select(id: Int): Customer? =
        try {
            CustomerTable
                .select { CustomerTable.id eq id }
                .map(ResultRow::toCustomer)
                .singleOrNull()
        } catch (e: Exception) {
            null
        }

    fun select(): List<Customer> =
        try {
            CustomerTable
                .selectAll()
                .map(ResultRow::toCustomer)
        } catch (e: Exception) {
            emptyList<Customer>()
        }

    fun delete(id: Int): Int =
        CustomerTable.deleteWhere { CustomerTable.id.eq(id) }
}