package com.onchain.wallet.base

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    open lateinit var binding: ViewDataBinding

    var lifecycleOwner: LifecycleOwner = when (context) {
        is AppCompatActivity -> context
        is Fragment -> context.viewLifecycleOwner
        else -> throw Exception("Context must be AppCompatActivity or Fragment!")
    }

    abstract fun createBinding(): ViewDataBinding
    open fun initView() {}
    open fun initListener() {}

    init {
        binding = createBinding()
        binding.lifecycleOwner = lifecycleOwner
        addView(binding.root)
        initView()
        initListener()
    }
}