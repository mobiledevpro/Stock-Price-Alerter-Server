package com.mobiledevpro

import com.mobiledevpro.models.Version
import com.mobiledevpro.plugins.configureHTTP
import com.mobiledevpro.plugins.configureRouting
import com.mobiledevpro.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSerialization()
    configureHTTP()
}

fun Application.moduleV1() {
    configureRouting(Version.V1)
}