package com.mobiledevpro.feature.customer.remote.route

import com.mobiledevpro.core.models.SuccessBody
import com.mobiledevpro.database.dao.customerDAO
import com.mobiledevpro.feature.customer.remote.model.CustomerRemote
import com.mobiledevpro.feature.customer.toLocal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerAdd(path: String) {
    post(path) {
        val customer = call.receive<CustomerRemote>()

        customerDAO
            .add(customer.toLocal())

        SuccessBody("Customer stored correctly")
            .also { body ->
                call.respond(HttpStatusCode.Created, body)
            }
    }
}
