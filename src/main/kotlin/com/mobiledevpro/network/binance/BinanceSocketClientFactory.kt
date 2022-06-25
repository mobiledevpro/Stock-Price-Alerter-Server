package com.mobiledevpro.network.binance

import com.mobiledevpro.network.binance.model.BinanceSocket
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.server.config.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
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
                this.pingInterval = 5000
            }
        }
    }

    suspend fun HttpClient.subscribe(request: BinanceSocket.Request) = flow<Frame.Text> {
        wss(host = HOST, path = "/ws") {
            println("Send ${request.method}")

            Json.encodeToString(request)
                .let(Frame::Text)
                .let {
                    send(it)
                }

            while (true) {
                if (!isActive) break
                try {
                    val othersMessage = incoming.receive() as? Frame.Text
                    othersMessage?.let {
                        emit(it)
                    }
                } catch (e: Exception) {
                    println("WebSocket exception: ${e.localizedMessage}")
                }
            }

        }
    }.cancellable()

    suspend fun HttpClient.unsubscribe(request: BinanceSocket.Request) {
        wss(host = HOST, path = "/ws") {
            println("Send ${request.method}")

            Json.encodeToString(request)
                .let(Frame::Text)
                .let {
                    send(it)
                }
        }
    }

    private const val HOST = "stream.binancefuture.com"
}