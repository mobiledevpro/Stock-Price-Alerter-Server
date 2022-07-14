package com.mobiledevpro.feature.crypto.userwatchlist.repository

import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker

interface CryptoUserWatchlistRepository {
    suspend fun getWatchlist(userId: String): List<CryptoUserWatchlistTicker>
}

val cryptoUserWatchlistRepository = ImplCryptoUserWatchlistRepository()