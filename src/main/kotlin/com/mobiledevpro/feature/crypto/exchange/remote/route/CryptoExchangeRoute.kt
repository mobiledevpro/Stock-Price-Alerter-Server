package com.mobiledevpro.feature.crypto.exchange.remote.route

import io.ktor.server.routing.*

fun Route.cryptoExchange() {
    val path = "crypto/exchange"
    cryptoExchangeListGetAll(path)
    cryptoExchangeListGetBySearch(path)
}