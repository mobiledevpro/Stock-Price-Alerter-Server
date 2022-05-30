package com.mobiledevpro.feature.customer

import com.mobiledevpro.database.model.CustomerTable
import com.mobiledevpro.feature.customer.local.model.Customer
import com.mobiledevpro.feature.customer.remote.model.CustomerRemote
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toCustomer(): Customer =
    Customer(
        id = this[CustomerTable.id],
        firstName = this[CustomerTable.firstName],
        lastName = this[CustomerTable.lastName]
    )

fun Customer.toRemote(): CustomerRemote =
    CustomerRemote(
        id, firstName, lastName
    )

fun CustomerRemote.toLocal(): Customer =
    Customer(
        id, firstName, lastName
    )