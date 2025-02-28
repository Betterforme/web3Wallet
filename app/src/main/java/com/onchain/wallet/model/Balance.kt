package com.onchain.wallet.model

data class Balance(
    val ok: Boolean,
    val wallet: List<Wallet>,
    val warning: String
)

data class Wallet(
    val amount: String,
    val currency: String
)