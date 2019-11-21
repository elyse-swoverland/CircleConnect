package com.elyseswoverland.circleconnect.ui.favorites

import com.elyseswoverland.circleconnect.models.Merchant

interface FavoritesCallback {
    fun goToBusinessCard(merchant: Merchant)
}