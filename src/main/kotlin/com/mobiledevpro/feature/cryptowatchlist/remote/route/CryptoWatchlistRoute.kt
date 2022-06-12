package com.mobiledevpro.feature.cryptowatchlist.remote.route

import io.ktor.server.routing.*

fun Route.cryptoWatchlist() {
    val path = "crypto/watchlist"
    cryptoWatchlistAdd(path)
}