package com.mobiledevpro.network.binance.model

import kotlinx.serialization.Serializable


object BinanceSocket {
    @Serializable
    class Request(
        val method: Method,
        val params: Array<String>,
        val id: Int
    )

    fun Method.toString() =
        when (this) {
            Method.SUBSCRIBE -> "SUBSCRIBE"
            Method.UNSUBSCRIBE -> "UNSUBSCRIBE"
        }

    enum class Method {
        SUBSCRIBE,
        UNSUBSCRIBE
    }
}
