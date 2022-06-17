package com.mobiledevpro.core.extension

import com.mobiledevpro.core.models.ErrorBody
import com.mobiledevpro.core.models.SuccessBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

fun HttpStatusCode.toErrorBody(message: String) =
    ErrorBody(
        this.value,
        message
    )

fun HttpStatusCode.toSuccessBody(message: String) =
    SuccessBody(message)

suspend fun PipelineContext<*, ApplicationCall>.errorRespond(status: HttpStatusCode, message: String) =
    status.toErrorBody(message)
        .let { body -> call.respond(status, body) }

suspend fun PipelineContext<*, ApplicationCall>.successRespond(status: HttpStatusCode, message: String) =
    status.toSuccessBody(message)
        .let { body -> call.respond(status, body) }
