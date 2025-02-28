package com.onchain.wallet.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onchain.wallet.R
import com.onchain.wallet.base.BaseWidget
import com.onchain.wallet.databinding.ViewCurrenciesBinding
import com.onchain.wallet.model.CurrencyData

class CurrenciesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseWidget(context, attrs, defStyleAttr) {
    lateinit var currencies: MutableList<CurrencyData>
    lateinit var adapter: CurrenciesAdapter

    override fun createBinding(): ViewDataBinding {
        return ViewCurrenciesBinding.inflate(LayoutInflater.from(context))
    }

    override fun initView() {
        super.initView()
        currencies = mutableListOf()
        adapter = CurrenciesAdapter(currencies)
    }

    override fun initListener() {
        with(binding as ViewCurrenciesBinding) {
            currenciesRecyclerView.adapter = adapter
        }
    }

    fun refresh(data: MutableList<CurrencyData>) {
        adapter.addAll(data)
    }

}

class CurrenciesAdapter(val currencies: MutableList<CurrencyData>) :
    RecyclerView.Adapter<CurrenciesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, null, false)
        return CurrenciesVH(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CurrenciesVH, position: Int) {
        val data = currencies[position]
        Glide.with(holder.currencyIcon.context).load(data.icon).placeholder(R.drawable.logo)
            .into(holder.currencyIcon)
        holder.currencyName.text = data.name
        holder.priceTv.text = "$" + data.price
        holder.balanceTv.text = data.balance +" "+ data.symbol
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    fun addAll(data: MutableList<CurrencyData>) {
        currencies.clear()
        currencies.addAll(data)
        notifyDataSetChanged()
    }
}

class CurrenciesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var currencyIcon: ImageView
    var currencyName: TextView
    var balanceTv: TextView
    var priceTv: TextView

    init {
        currencyName = itemView.findViewById(R.id.currencyName)
        currencyIcon = itemView.findViewById(R.id.currencyIcon)
        balanceTv = itemView.findViewById(R.id.balanceTv)
        priceTv = itemView.findViewById(R.id.priceTv)
    }
}