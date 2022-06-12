package com.mobiledevpro.feature.cryptowatchlist.remote.route

import com.mobiledevpro.core.models.SuccessBody
import com.mobiledevpro.feature.cryptocoin.remote.model.CryptoCoinRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoWatchlistAdd(path: String) {
    route(path) {
        put {
            val coin = call.receive<CryptoCoinRemote>()

            println("Add coin ${coin.symbol}")

            //TODO: add to watchlist
/*
            customerDAO
                .add(customer.toLocal())

 */

            SuccessBody("Coin added to watchlist")
                .also { body ->
                    call.respond(HttpStatusCode.Created, body)
                }
        }
    }
}