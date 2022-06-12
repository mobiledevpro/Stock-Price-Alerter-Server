package com.mobiledevpro.feature.cryptowatchlist.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.core.models.SuccessBody
import com.mobiledevpro.database.dao.cryptoCoinDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoWatchlistAdd(path: String) {
    route("$path/{symbol?}") {
        put {
            val coin: String = call.parameters["symbol"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            val searchCoinList = cryptoCoinDAO.selectBy(coin)

            //Check this coin exist in the coins list

            if (searchCoinList.isEmpty())
                return@put ErrorBody(HttpStatusCode.NotFound.value, "Coin Not Found")
                    .let { body ->
                        call.respond(HttpStatusCode.NotFound, body)
                    }

            if (searchCoinList.size > 1)
                return@put ErrorBody(HttpStatusCode.BadRequest.value, "Wrong coin symbol. There is more than 1 found")
                    .let { body ->
                        call.respond(HttpStatusCode.BadRequest, body)
                    }

            //TODO: add to watchlist

            SuccessBody("Coin added to watchlist")
                .also { body ->
                    call.respond(HttpStatusCode.Created, body)
                }
        }
    }
}