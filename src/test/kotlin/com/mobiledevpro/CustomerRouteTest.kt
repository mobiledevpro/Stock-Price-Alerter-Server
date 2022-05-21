package com.mobiledevpro

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerRouteTest {
    @Test
    fun testGetCustomers() = testApplication {
        client.get("/customer").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    /*fun testAddCustomer() = testApplication {
        val customer = Customer(1, "John", " Black")
        val request = HttpRequestBuilder()
        //client.post("customer", HttpRequestBuilder()).apply {

        }
    }

     */
}