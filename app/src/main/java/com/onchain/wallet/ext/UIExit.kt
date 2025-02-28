package com.onchain.wallet.ext

import com.onchain.wallet.App


fun Float.dp2px(): Int {
    val scale = App.instance.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.dp2px(): Int {
    val scale = App.instance.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Float.px2dp(): Int {
    val scale = App.instance.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

fun Int.px2dp(): Int {
    val scale = App.instance.resources.displayMetrics.density
    return (this / scale + 0.5f).toInt()
}



