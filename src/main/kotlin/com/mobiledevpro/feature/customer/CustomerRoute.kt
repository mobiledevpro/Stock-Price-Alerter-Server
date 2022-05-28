package com.mobiledevpro.feature.customer

import io.ktor.server.routing.*

fun Route.customerRoute() {
    val path = "/customer"
    customerGet(path)
    customerAdd(path)
    customerDelete(path)
}