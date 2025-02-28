package com.onchain.wallet

import android.text.SpannableString
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.onchain.wallet.base.BaseActivity
import com.onchain.wallet.databinding.ActivityMainBinding
import com.onchain.wallet.ext.dp2px
import com.onchain.wallet.vm.WalletViewModel
import kotlinx.coroutines.launch


class MainActivity : BaseActivity<WalletViewModel>() {

    val binding: ActivityMainBinding
        get() {
            return viewBinding as ActivityMainBinding
        }

    override fun flowOfGetData() {
        super.flowOfGetData()
        viewModel.getData()
    }

    override fun flowOfSetupData() {
        super.flowOfSetupData()
        lifecycleScope.launch {
            viewModel.currencyData.collect {
                binding.currenciesRecyclerView.refresh(it)
            }
        }
        lifecycleScope.launch {
            viewModel.currencyTotalBalance.collect {
                binding.balanceTv.text = balanceSpan("$ $it USD")
            }
        }
    }

    override fun createBinding(): ViewDataBinding {
        return ActivityMainBinding.inflate(baseInflate).apply {
            owner = this@MainActivity
        }
    }

    private fun balanceSpan(content: String) = SpannableString(content).apply {
        setSpan(
            ForegroundColorSpan(getColor(R.color.white)),
            content.indexOf("$") + 1,
            content.indexOf("USD"),
            SpannedString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(
            AbsoluteSizeSpan(20.dp2px()),
            content.indexOf("$") + 1,
            content.indexOf("USD"),
            SpannedString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

