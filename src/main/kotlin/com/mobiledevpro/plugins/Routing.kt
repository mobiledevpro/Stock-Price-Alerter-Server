package com.mobiledevpro.plugins

import com.mobiledevpro.models.Version
import com.mobiledevpro.routes.customer.customerRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(version: Version) {

    routing {
        get("/") {
            call.respondText("API Server")
        }
    }
    routing {
        when (version) {
            Version.V1 -> configV1()
            Version.V2 -> configV2()
        }
    }
}

private fun Routing.configV1() {
    route("/v1") {
        customerRoute()
    }
}

private fun Routing.configV2() {
    route("/v2") {
        customerRoute()
    }
}