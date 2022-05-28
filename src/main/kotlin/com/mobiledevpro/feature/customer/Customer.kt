package com.mobiledevpro.feature.customer

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String
)

val customerStorage = mutableListOf<Customer>()
