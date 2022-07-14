package com.mobiledevpro

import com.mobiledevpro.core.models.Version
import com.mobiledevpro.core.module.binanceExchangeInfoModule
import com.mobiledevpro.core.module.binanceTickersModule
import com.mobiledevpro.core.plugins.configureHTTP
import com.mobiledevpro.core.plugins.configureRouting
import com.mobiledevpro.core.plugins.configureSerialization
import com.mobiledevpro.core.plugins.configureWebSocketServer
import com.mobiledevpro.database.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.moduleMain() {
    configureSerialization()
    configureHTTP()
    configureWebSocketServer()
    DatabaseFactory.init(environment.config)
}

fun Application.moduleRoutingV1() {
    configureRouting(Version.V1)
}

fun Application.binanceModule() {
    binanceExchangeInfoModule()
    binanceTickersModule()
}