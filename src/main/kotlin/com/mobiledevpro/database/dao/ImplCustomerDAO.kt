package com.mobiledevpro.database.dao

import com.mobiledevpro.database.DatabaseFactory.dbQuery
import com.mobiledevpro.database.model.CustomerTable
import com.mobiledevpro.feature.customer.local.model.Customer


class ImplCustomerDAO : CustomerDAO {
    override suspend fun selectAll(): List<Customer> = dbQuery {
        CustomerTable.select()
    }

    override suspend fun select(customerId: Int): Customer? = dbQuery {
        CustomerTable.select(customerId)
    }

    override suspend fun add(customer: Customer): Boolean = dbQuery {
        val result = CustomerTable.insert(customer).resultedValues

        (result?.size ?: 0) > 0
    }

    override suspend fun delete(customerId: Int): Boolean = dbQuery {
        CustomerTable.delete(customerId) > 0
    }
}

//TODO: integrate DI and move this line to DI
val customerDAO: CustomerDAO = ImplCustomerDAO()