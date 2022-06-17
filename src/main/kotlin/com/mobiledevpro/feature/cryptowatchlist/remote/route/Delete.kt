package com.mobiledevpro.feature.cryptowatchlist.remote.route

import com.mobiledevpro.core.extension.errorRespond
import com.mobiledevpro.core.extension.successRespond
import com.mobiledevpro.database.dao.cryptoWatchlistDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoWatchlistDelete(path: String) {
    route("$path/{user_id?}/{symbol?}") {
        delete {

            val userId: String = call.parameters["user_id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            val symbol: String = call.parameters["symbol"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            //Check this ticker was not added before
            cryptoWatchlistDAO.isExist(userId, symbol)
                .let { isExist ->
                    if (isExist)
                        cryptoWatchlistDAO.delete(userId, symbol)
                            .let { isDeleted ->
                                if (isDeleted)
                                    return@delete successRespond(
                                        HttpStatusCode.OK,
                                        "Ticker $symbol removed from the watchlist"
                                    )
                                else
                                    return@delete successRespond(
                                        HttpStatusCode.OK,
                                        "Ticker $symbol is not removed from the watchlist. Something went wrong"
                                    )
                            }
                    else
                        return@delete errorRespond(
                            HttpStatusCode.NotFound,
                            "Ticker $symbol is not found in the watchlist"
                        )
                }
        }
    }
}