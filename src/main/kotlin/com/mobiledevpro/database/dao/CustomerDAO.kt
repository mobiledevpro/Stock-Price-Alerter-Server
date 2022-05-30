package com.mobiledevpro.database.dao

import com.mobiledevpro.feature.customer.local.model.Customer

interface CustomerDAO {
    suspend fun selectAll(): List<Customer>
    suspend fun select(customerId: Int): Customer?
    suspend fun add(customer: Customer): Boolean
    suspend fun delete(customerId: Int): Boolean
}