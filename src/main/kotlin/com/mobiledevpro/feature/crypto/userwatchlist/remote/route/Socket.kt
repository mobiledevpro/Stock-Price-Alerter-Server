package com.mobiledevpro.feature.crypto.userwatchlist.remote.route

import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker
import com.mobiledevpro.feature.crypto.userwatchlist.repository.cryptoUserWatchlistRepository
import com.mobiledevpro.feature.crypto.userwatchlist.toRemote
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.time.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Duration

fun Route.cryptoUserWatchlistSocket(path: String) {
    webSocket("$path/{user_id?}") {
        val userId: String? = call.parameters["user_id"]
/*
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    val receivedText = frame.readText()
                    if (receivedText.equals("unsubscribe", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Unsubscribed. Good bye."))
                    }
                }
                else -> {}
            }
        }

 */

        while (true) {
            delay(Duration.ofSeconds(3))
            println("User $userId")

            userId?.let {

                //TODO: check if there a registered user

                //Getting user's watchlist
                cryptoUserWatchlistRepository.getWatchlist(userId)
                    .let(List<CryptoUserWatchlistTicker>::toRemote)
                    .let { watchlist ->
                        Json.encodeToString(watchlist)
                    }.let { jsonStr ->
                        send(Frame.Text(jsonStr))
                    }

            } ?: kotlin.run {
                close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "User ID is empty"))
            }
        }


    }
}