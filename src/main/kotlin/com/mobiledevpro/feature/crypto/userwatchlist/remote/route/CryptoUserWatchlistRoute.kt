package com.mobiledevpro.feature.crypto.userwatchlist.remote.route

import io.ktor.server.routing.*

fun Route.cryptoUserWatchlist() {
    val path = "crypto/watchlist"
    cryptoUserWatchlistAdd(path)
    cryptoUserWatchlistDelete(path)
    cryptoUserWatchlistGet(path)
    cryptoUserWatchlistSocket(path)
}