package com.mobiledevpro

import com.mobiledevpro.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun testCustomer() = testApplication {
        application {
            configureRouting()
        }

        client.get("/customer").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("No customers found", bodyAsText())
        }
    }
}