package com.mobiledevpro.feature.customer.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerRemote(
    val id: Int? = null,
    val firstName: String,
    val lastName: String
)
