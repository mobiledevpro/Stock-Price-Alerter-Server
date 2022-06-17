package com.mobiledevpro.feature.cryptowatchlist.remote.route

import com.mobiledevpro.core.extension.errorRespond
import com.mobiledevpro.database.dao.cryptoWatchlistDAO
import com.mobiledevpro.feature.cryptowatchlist.local.CryptoWatchlistTicker
import com.mobiledevpro.feature.cryptowatchlist.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoWatchlistGet(path: String) {
    route("$path/{user_id?}") {
        get {

            val userId: String = call.parameters["user_id"]
                ?: return@get errorRespond(HttpStatusCode.BadRequest, "No User ID")

            val watchlist = cryptoWatchlistDAO.select(userId)
                .map(CryptoWatchlistTicker::toRemote)

            if (watchlist.isNotEmpty())
                return@get call.respond(watchlist)
            else {
                return@get errorRespond(HttpStatusCode.NotFound, "Watchlist is empty")
            }
        }
    }
}