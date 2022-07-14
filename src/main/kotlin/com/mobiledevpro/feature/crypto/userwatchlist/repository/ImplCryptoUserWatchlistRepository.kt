package com.mobiledevpro.feature.crypto.userwatchlist.repository

import com.mobiledevpro.database.dao.cryptoUserWatchlistDAO
import com.mobiledevpro.feature.crypto.userwatchlist.local.model.CryptoUserWatchlistTicker

class ImplCryptoUserWatchlistRepository : CryptoUserWatchlistRepository {
    override suspend fun getWatchlist(userId: String): List<CryptoUserWatchlistTicker> =
        cryptoUserWatchlistDAO.select(userId)
}