package com.mobiledevpro

import com.mobiledevpro.core.models.Version
import com.mobiledevpro.core.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals


class CryptoExchangeTest {

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting(Version.V1)
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("API Server", bodyAsText())
        }
    }
    /*@Test
    fun testGetAll() = testApplication {
      val response = client.get("v1/crypto/exchange").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

     */
/*
    @Test
    fun testRoot() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world!", response.bodyAsText())
    }

    @Test
    fun testRootLegacyApi() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello, world!", response.content)
            }
        }
    }

 */
}