package com.onchain.wallet.ext

import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.onchain.wallet.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext


fun String.readAssets():String? {
    try {
        val inputReader = InputStreamReader(App.instance.resources.assets.open(this))
        val bufReader = BufferedReader(inputReader)

        var line: String?
        val result = StringBuilder()

        do {
            line = bufReader.readLine()
            if (line != null) {
                result.append(line)
            } else {
                break
            }
        } while (true)

        return result.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun String?.toast(
    gravity: Int = Gravity.BOTTOM,
    x: Int = 0,
    y: Int = 100.dp2px(),
    view: View? = null
) {
    runOnMainThread {
        val toast = Toast.makeText(App.instance, this ?: return@runOnMainThread, Toast.LENGTH_LONG)
        toast.setGravity(gravity, x, y)
        view?.let {
            toast.view = it
        }
        toast.show()
    }
}

/**
 * 切换主线程
 */
fun runOnMainThread(block: NoArgClosure) {
    runOnTargetThread(Dispatchers.Main, block)
}

private fun runOnTargetThread(context: CoroutineContext, block: NoArgClosure) {
    GlobalScope.launch(context) { block() }
}

typealias NoArgClosure = () -> Unit