package com.mobiledevpro.core.models

import kotlinx.coroutines.channels.Channel

//notify BinanceTickersModule to re-subscribe on price updates
val watchlistInsertDeleteChannel = Channel<None>()