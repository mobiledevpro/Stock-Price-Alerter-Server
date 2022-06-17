package com.mobiledevpro.network.binance.model

import kotlinx.serialization.Serializable

@Serializable
class BinanceSocketRequest(
    val method: String,
    val params: Array<String>,
    val id: Int
)

enum class Method {
    SUBSCRIBE,
    UNSUBSCRIBE
}