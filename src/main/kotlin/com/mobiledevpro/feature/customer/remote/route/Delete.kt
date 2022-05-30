package com.mobiledevpro.feature.customer.remote.route

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.core.models.SuccessBody
import com.mobiledevpro.database.dao.customerDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerDelete(path: String) {
    delete("$path/{id?}") {

        val id: Int = call.parameters["id"]?.toInt()
            ?: return@delete call.respond(HttpStatusCode.BadRequest)

        val isDeleted =
            customerDAO
                .delete(id)

        if (isDeleted)
            SuccessBody("Customer removed correctly")
                .also { body ->
                    call.respond(HttpStatusCode.Accepted, body)
                }
        else
            ErrorBody(HttpStatusCode.NotFound.value, "Customer Not Found")
                .also { body ->
                    call.respond(HttpStatusCode.NotFound, body)
                }

    }
}