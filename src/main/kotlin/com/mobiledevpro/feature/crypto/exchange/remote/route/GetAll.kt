package com.mobiledevpro.feature.crypto.exchange.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.database.dao.cryptoExchangeDAO
import com.mobiledevpro.feature.crypto.exchange.local.CryptoExchange
import com.mobiledevpro.feature.crypto.exchange.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.cryptoExchangeListGetAll(path: String) {
    route(path) {
        get {

            //select from database
            val coinList =
                cryptoExchangeDAO
                    .selectAll()
                    .map(CryptoExchange::toRemote)

            if (coinList.isNotEmpty())
                call.respond(coinList)
            else {
                ErrorBody(HttpStatusCode.NotFound.value, "No exchange info found").also { body ->
                    call.respond(HttpStatusCode.OK, body)
                }
            }
        }
    }
}