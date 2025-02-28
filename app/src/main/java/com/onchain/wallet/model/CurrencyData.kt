package com.onchain.wallet.model

data class CurrencyData(
    val icon: String,
    val name: String,
    val symbol: String,
    var balance: String? = null,
    var price: String? = null,
    var rate: String? = null
)