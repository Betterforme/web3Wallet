package com.onchain.wallet.repo

import com.google.gson.Gson
import com.onchain.wallet.ext.readAssets
import com.onchain.wallet.ext.toast
import com.onchain.wallet.model.Balance
import com.onchain.wallet.model.CurrencyList
import com.onchain.wallet.model.RateList
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip

class WalletRepo {
    fun getCurrencies() = flow {
        val currencyList = Gson().fromJson("currencies.json".readAssets(), CurrencyList::class.java)
        if (currencyList.ok) {
            emit(currencyList.currencies)
        }
    }


    fun getRates() = flow {
        val rates = Gson().fromJson("live-rates.json".readAssets(), RateList::class.java)
        if (rates.ok) {
            emit(rates.tiers)
        } else {
            rates.warning.toast()
        }
    }


    fun getBalance() = flow {
        val balances = Gson().fromJson("wallet-balance.json".readAssets(), Balance::class.java)
        if (balances.ok) {
            emit(balances.wallet)
        } else {
            balances.warning.toast()
        }
    }

    fun getRateAndBalance() =
        getRates().zip(getBalance()) { rate, balance -> Pair(rate, balance) }
}