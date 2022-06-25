package com.mobiledevpro.feature.crypto.exchange.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.database.dao.cryptoExchangeDAO
import com.mobiledevpro.feature.crypto.exchange.local.CryptoExchange
import com.mobiledevpro.feature.crypto.exchange.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.cryptoExchangeListGetBySearch(path: String) {
    route(path) {
        get("{search?}") {

            val symbol: String = call.parameters["search"]
                ?: return@get ErrorBody(HttpStatusCode.BadRequest.value, "Missing search value")
                    .let { body ->
                        call.respond(HttpStatusCode.BadRequest, body)
                    }

            val coinList =
                cryptoExchangeDAO
                    .searchBy(symbol)
                    .map(CryptoExchange::toRemote)

            if (coinList.isEmpty())
                return@get ErrorBody(HttpStatusCode.NotFound.value, "Nothing found for $symbol").let { body ->
                    call.respond(HttpStatusCode.NotFound, body)
                }
            else
                call.respond(coinList)
        }
    }
}