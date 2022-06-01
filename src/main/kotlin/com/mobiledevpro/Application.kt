package com.mobiledevpro

import com.mobiledevpro.core.models.Version
import com.mobiledevpro.core.plugins.configureHTTP
import com.mobiledevpro.core.plugins.configureRouting
import com.mobiledevpro.core.plugins.configureSerialization
import com.mobiledevpro.database.DatabaseFactory
import com.mobiledevpro.network.HttpClientFactory
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.async

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSerialization()
    configureHTTP()
}

fun Application.moduleV1() {
    DatabaseFactory.init(environment.config)
    configureRouting(Version.V1)

    async {
        HttpClientFactory.init(environment.config)
    }
}