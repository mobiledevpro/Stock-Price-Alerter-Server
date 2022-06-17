package com.mobiledevpro.network.binance

import com.mobiledevpro.network.binance.model.BinanceSocketRequest
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.server.config.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Socket client
 *
 * NOTE:
 * Read more https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md
 */
object BinanceSocketClientFactory {
    lateinit var binanceSocketClient: HttpClient

    fun init(config: ApplicationConfig) {
        binanceSocketClient = HttpClient(OkHttp) {
            install(WebSockets) {
                this.pingInterval = 1000
            }
        }
    }

    suspend fun HttpClient.subscribe() {
        val request = BinanceSocketRequest(
            method = "SUBSCRIBE",
            params = arrayOf("btcusdt@ticker"/*, "ethusdt@bookTicker"*/),
            1
        )

        wss(host = HOST, path = "/ws") {

            Json.encodeToString(request)
                .let(Frame::Text)
                .let {
                    send(it)
                }
            while (true) {
                val othersMessage = incoming.receive() as? Frame.Text
                println(othersMessage?.readText())
                /*  val myMessage = Scanner(System.`in`).next()
                  if(myMessage != null) {
                      send(myMessage)
                  }*/
            }
        }
    }

    suspend fun HttpClient.unsubscribe() {

    }

    private const val HOST = "stream.binancefuture.com"
}