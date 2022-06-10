package com.mobiledevpro.feature.cryptocoinlist.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.database.dao.cryptoCoinDAO
import com.mobiledevpro.feature.cryptocoinlist.local.CryptoCoin
import com.mobiledevpro.feature.cryptocoinlist.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.cryptoCoinListGetBySearch(path: String) {
    route(path) {
        get("{search?}") {

            val symbol: String = call.parameters["search"]
                ?: return@get ErrorBody(HttpStatusCode.BadRequest.value, "Missing search value")
                    .let { body ->
                        call.respond(HttpStatusCode.BadRequest, body)
                    }

            val coinList =
                cryptoCoinDAO
                    .selectBy(symbol)
                    .map(CryptoCoin::toRemote)

            if (coinList.isEmpty())
                return@get ErrorBody(HttpStatusCode.NotFound.value, "Nothing found for $symbol").let { body ->
                    call.respond(HttpStatusCode.NotFound, body)
                }
            else
                call.respond(coinList)
        }
    }
}