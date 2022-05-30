package com.mobiledevpro.feature.customer.remote.route

import io.ktor.server.routing.*

fun Route.customerRoute() {
    val path = "/customer"
    customerGet(path)
    customerAdd(path)
    customerDelete(path)
}