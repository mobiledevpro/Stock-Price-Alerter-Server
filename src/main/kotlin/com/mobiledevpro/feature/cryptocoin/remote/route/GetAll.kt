package com.mobiledevpro.feature.cryptocoin.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.database.dao.cryptoCoinDAO
import com.mobiledevpro.feature.cryptocoin.local.CryptoCoin
import com.mobiledevpro.feature.cryptocoin.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.cryptoCoinListGetAll(path: String) {
    route(path) {
        get {

            //select from database
            val coinList =
                cryptoCoinDAO
                    .selectAll()
                    .map(CryptoCoin::toRemote)

            if (coinList.isNotEmpty())
                call.respond(coinList)
            else {
                ErrorBody(HttpStatusCode.NotFound.value, "No crypto coin found").also { body ->
                    call.respond(HttpStatusCode.OK, body)
                }
            }
        }
    }
}