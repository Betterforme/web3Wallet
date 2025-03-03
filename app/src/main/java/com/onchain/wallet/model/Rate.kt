package com.onchain.wallet.model

data class RateList(
    val ok: Boolean,
    val tiers: List<Tier>,
    val warning: String
)

data class Tier(
    val from_currency: String,
    val rates: List<Rate>,
    val time_stamp: Int,
    val to_currency: String
)

data class Rate(
    val amount: String,
    val rate: String
)