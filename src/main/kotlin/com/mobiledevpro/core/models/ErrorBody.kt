package com.mobiledevpro.core.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    val code: Int,
    val message: String
)