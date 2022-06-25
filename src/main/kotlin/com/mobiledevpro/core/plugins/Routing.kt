package com.mobiledevpro.core.plugins

import com.mobiledevpro.core.models.Version
import com.mobiledevpro.feature.crypto.exchange.remote.route.cryptoExchange
import com.mobiledevpro.feature.crypto.userwatchlist.remote.route.cryptoUserWatchlist

import com.mobiledevpro.feature.customer.remote.route.customerRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(version: Version) {

    routing {
        get("/") {
            call.respondText("API Server")
        }
    }
    routing {
        when (version) {
            Version.V1 -> configV1()
            else -> throw RuntimeException("Wrong endpoint version $version")
        }
    }
}

private fun Routing.configV1() {
    route("v1") {
        customerRoute()
        cryptoExchange()
        cryptoUserWatchlist()
    }
}