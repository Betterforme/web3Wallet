package com.onchain.wallet.vm

import androidx.lifecycle.viewModelScope
import com.onchain.wallet.base.BaseViewModel
import com.onchain.wallet.model.CurrencyData
import com.onchain.wallet.repo.WalletRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class WalletViewModel : BaseViewModel() {
    private val repo = WalletRepo()
    private var job: Job? = null

    private val _currencyData = MutableStateFlow<MutableList<CurrencyData>>(mutableListOf())
    private val _currencyTotalBalance = MutableStateFlow("")
    val currencyData: StateFlow<MutableList<CurrencyData>> = _currencyData
    val currencyTotalBalance: StateFlow<String> = _currencyTotalBalance

    fun getData() {
        job = viewModelScope.launch {
            repo.getCurrencies().combine(repo.getRateAndBalance()) { currencies, balanceRatePair ->
                val currenciesData = mutableListOf<CurrencyData>()
                var currencyTotalBalance = "0"

                currencies.map { currency ->
                    val currencyData =
                        CurrencyData(currency.colorful_image_url, currency.name, currency.symbol)

                    balanceRatePair.first.find { rate -> currency.symbol == rate.from_currency }
                        ?.also { rate ->
                            currencyData.rate = rate.rates.firstOrNull()?.rate ?: "0"
                        }
                    balanceRatePair.second.find { balance -> currency.symbol == balance.currency }
                        ?.also { balance ->
                            currencyData.balance = balance.amount
                        }

                    currencyData.price =
                        BigDecimal(currencyData.balance).multiply(BigDecimal(currencyData.rate))
                            .setScale(7, RoundingMode.DOWN).stripTrailingZeros().toPlainString()

                    currencyTotalBalance =
                        BigDecimal(currencyTotalBalance).plus(BigDecimal(currencyData.price))
                            .toPlainString()

                    currenciesData.add(currencyData)
                }
                Pair(currenciesData, currencyTotalBalance)
            }.collect { result ->
                _currencyData.value = result.first
                _currencyTotalBalance.value = result.second
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}