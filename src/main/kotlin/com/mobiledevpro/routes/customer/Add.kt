package com.mobiledevpro.routes.customer

import com.mobiledevpro.models.Customer
import com.mobiledevpro.models.SuccessBody
import com.mobiledevpro.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerAdd(path: String) {
    post(path) {
        val customer = call.receive<Customer>()
        customerStorage.add(customer)

        SuccessBody("Customer stored correctly")
            .also { body ->
                call.respond(HttpStatusCode.Created, body)
            }
    }
}
