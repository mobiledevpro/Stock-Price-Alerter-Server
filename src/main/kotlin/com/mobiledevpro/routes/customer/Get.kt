package com.mobiledevpro.routes.customer

import com.mobiledevpro.models.ErrorBody
import com.mobiledevpro.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerGet(path: String) {
    route(path) {
        get {
            if (customerStorage.isNotEmpty())
                call.respond(customerStorage)
            else {
                ErrorBody(HttpStatusCode.NotFound.value, "No customers found")
                    .also { body ->
                        call.respond(HttpStatusCode.OK, body)
                    }
            }
        }
        get("{id?}") {
            val id: Int = call.parameters["id"]?.toInt()
                ?: return@get
            ErrorBody(HttpStatusCode.BadRequest.value, "Missing id")
                .let { body ->
                    call.respond(HttpStatusCode.BadRequest, body)
                }


            val customer = customerStorage.find { it.id == id }
                ?: return@get
            ErrorBody(HttpStatusCode.NotFound.value, "No customer with id $id")
                .let { body ->
                    call.respond(HttpStatusCode.NotFound, body)
                }

            call.respond(customer)
        }
    }
}