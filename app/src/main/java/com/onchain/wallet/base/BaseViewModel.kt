package com.onchain.wallet.base

import android.content.Intent
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel(){
    open fun onCreate() = Unit

    final override fun onCleared() {
        onDestroy()
    }

    open fun onDestroy() = Unit

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) = Unit
}