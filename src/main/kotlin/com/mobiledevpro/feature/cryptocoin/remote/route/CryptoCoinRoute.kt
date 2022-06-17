package com.mobiledevpro.feature.cryptocoin.remote.route

import io.ktor.server.routing.*

fun Route.cryptoCoin() {
    val path = "crypto/coin"
    cryptoCoinListGetAll(path)
    cryptoCoinListGetBySearch(path)
}