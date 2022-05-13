package com.mobiledevpro.plugins

import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureHTTP() {
    install(ConditionalHeaders)

}
