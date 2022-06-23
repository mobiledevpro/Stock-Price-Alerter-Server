package com.mobiledevpro.feature.crypto.userwatchlist.remote.route

import com.mobiledevpro.core.extension.errorRespond
import com.mobiledevpro.core.extension.successRespond
import com.mobiledevpro.database.dao.cryptoExchangeDAO
import com.mobiledevpro.database.dao.cryptoUserWatchlistDAO
import com.mobiledevpro.database.dao.cryptoWatchlistDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoUserWatchlistAdd(path: String) {
    route("$path/{user_id?}/{symbol?}") {
        put {

            val userId: String = call.parameters["user_id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            val symbol: String = call.parameters["symbol"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)

            //Check this ticker is available for trading
            val searchCoinList = cryptoExchangeDAO.selectBy(symbol)
            if (searchCoinList.isEmpty())
                return@put errorRespond(HttpStatusCode.NotFound, "Ticker $symbol is not found")

            if (searchCoinList.size > 1)
                return@put errorRespond(HttpStatusCode.BadRequest, "Wrong ticker symbol. There is more than 1 found")

            //Check this ticker was not added for this user before
            cryptoUserWatchlistDAO.isExist(userId, symbol)
                .let { isAlreadyAdded ->
                    if (isAlreadyAdded)
                        return@put errorRespond(HttpStatusCode.Conflict, "Ticker $symbol is already added")
                }

            //Add ticker to common watchlist
            cryptoWatchlistDAO.also { dao ->
                if (!dao.isExist(symbol))
                    dao.add(symbol)
            }

            //Add ticker to user's watchlist table
            cryptoUserWatchlistDAO.add(userId, symbol)
                .let { isAdded ->
                    if (!isAdded)
                        return@put errorRespond(
                            HttpStatusCode.BadRequest,
                            "Ticker $symbol is not added. Something went wrong"
                        )
                    else
                        return@put successRespond(HttpStatusCode.Created, "Ticker added to watchlist")
                }
        }
    }
}