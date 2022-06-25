package com.mobiledevpro.feature.crypto.userwatchlist.remote.route

import com.mobiledevpro.core.extension.errorRespond
import com.mobiledevpro.core.extension.successRespond
import com.mobiledevpro.core.models.None
import com.mobiledevpro.core.models.watchlistInsertDeleteChannel
import com.mobiledevpro.database.dao.cryptoUserWatchlistDAO
import com.mobiledevpro.database.dao.cryptoWatchlistDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoUserWatchlistDelete(path: String) {
    route("$path/{user_id?}/{symbol?}") {
        delete {

            val userId: String = call.parameters["user_id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            val symbol: String = call.parameters["symbol"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            //Check this ticker was not added before
            cryptoUserWatchlistDAO.isExist(userId, symbol)
                .let { isExist ->
                    if (isExist)
                        cryptoUserWatchlistDAO.delete(userId, symbol)
                            .let { isDeleted ->
                                //Check does this symbol exist for any other user
                                cryptoUserWatchlistDAO.isExist(symbol)
                                    .also { isExist ->
                                        if (!isExist) {
                                            //delete symbol from tickers list to getting price updates
                                            cryptoWatchlistDAO.delete(symbol)
                                            //notify BinanceTickersModule to re-subscribe on price updates
                                            watchlistInsertDeleteChannel.send(None())
                                        }
                                    }

                                isDeleted
                            }
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