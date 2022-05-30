package com.mobiledevpro.feature.customer.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.database.dao.customerDAO
import com.mobiledevpro.feature.customer.local.model.Customer
import com.mobiledevpro.feature.customer.toRemote
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerGet(path: String) {
    route(path) {
        get {

            //select from database
            val customerList =
                customerDAO
                    .selectAll()
                    .map(Customer::toRemote)

            if (customerList.isNotEmpty())
                call.respond(customerList)
            else {
                ErrorBody(HttpStatusCode.NotFound.value, "No customers found").also { body ->
                    call.respond(HttpStatusCode.OK, body)
                }
            }
        }
        get("{id?}") {
            val id: Int = call.parameters["id"]?.toInt()
                ?: return@get ErrorBody(HttpStatusCode.BadRequest.value, "Missing id")
                    .let { body ->
                        call.respond(HttpStatusCode.BadRequest, body)
                    }

            val customer = customerDAO.select(id)
                ?: return@get ErrorBody(HttpStatusCode.NotFound.value, "No customer with id $id").let { body ->
                    call.respond(HttpStatusCode.NotFound, body)
                }

            call.respond(customer.toRemote())
        }
    }
}