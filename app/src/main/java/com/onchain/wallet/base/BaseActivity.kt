package com.onchain.wallet.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onchain.wallet.util.GenericUtil
import kotlin.reflect.KClass


abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    lateinit var viewModel: VM
        private set

    open val baseInflate: LayoutInflater by lazy {
        LayoutInflater.from(this)
    }

    lateinit var viewBinding:ViewDataBinding

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()

        viewBinding = createBinding()
        viewBinding.lifecycleOwner = this
        setContentView(viewBinding.root)

        flowOfGetData()
        flowOfSetupData()
        flowOfInitUi()
    }

    abstract fun createBinding(): ViewDataBinding

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[requireNotNull(GenericUtil.getClassType(this, ViewModel::class) as KClass<VM>).java]
    }

    /***
     * 获取数据
     */
    open fun flowOfGetData() {}

    /**
     * 数据安装部分
     */
    open fun flowOfSetupData() {}

    open fun flowOfInitUi(){}
}