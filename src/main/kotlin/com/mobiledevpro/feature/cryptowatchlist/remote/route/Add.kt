package com.mobiledevpro.feature.cryptowatchlist.remote.route

import com.mobiledevpro.core.extension.errorRespond
import com.mobiledevpro.core.extension.successRespond
import com.mobiledevpro.database.dao.cryptoCoinDAO
import com.mobiledevpro.database.dao.cryptoWatchlistDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoWatchlistAdd(path: String) {
    route("$path/{user_id?}/{symbol?}") {
        put {

            val userId: String = call.parameters["user_id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            val symbol: String = call.parameters["symbol"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            //Check this ticker is available for trading
            val searchCoinList = cryptoCoinDAO.selectBy(symbol)
            if (searchCoinList.isEmpty())
                return@put errorRespond(HttpStatusCode.NotFound, "Ticker $symbol is not found")

            if (searchCoinList.size > 1)
                return@put errorRespond(HttpStatusCode.BadRequest, "Wrong ticker symbol. There is more than 1 found")

            //Check this ticker was not added before
            cryptoWatchlistDAO.isExist(userId, symbol)
                .let { isAlreadyAdded ->
                    if (isAlreadyAdded)
                        return@put errorRespond(HttpStatusCode.Conflict, "Ticker $symbol is already added")
                }

            //Add ticker to watchlist table
            cryptoWatchlistDAO.add(userId, symbol)
                .let { isAdded ->
                    if (!isAdded)
                        return@put errorRespond(
                            HttpStatusCode.BadRequest,
                            "Ticker $symbol is not added. Something went wrong"
                        )
                    else
                        return@put successRespond(HttpStatusCode.Created, "Coin added to watchlist")
                }
        }
    }
}