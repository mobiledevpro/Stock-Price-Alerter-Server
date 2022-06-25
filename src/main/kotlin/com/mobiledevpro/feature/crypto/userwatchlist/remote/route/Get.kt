package com.mobiledevpro.feature.crypto.userwatchlist.remote.route

import com.mobiledevpro.core.extension.errorRespond
import com.mobiledevpro.database.dao.cryptoUserWatchlistDAO
import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker
import com.mobiledevpro.feature.crypto.userwatchlist.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cryptoUserWatchlistGet(path: String) {
    route("$path/{user_id?}") {
        get {

            val userId: String = call.parameters["user_id"]
                ?: return@get errorRespond(HttpStatusCode.BadRequest, "No User ID")

            val watchlist = cryptoUserWatchlistDAO.select(userId)
                .map(CryptoUserWatchlistTicker::toRemote)

            if (watchlist.isNotEmpty())
                return@get call.respond(watchlist)
            else {
                return@get errorRespond(HttpStatusCode.NotFound, "Watchlist is empty")
            }
        }
    }
}