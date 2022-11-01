package com.assignment.one.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.assignment.one.R
import com.assignment.one.activity.ProductDetailsActivity
import com.assignment.one.model.Product
import kotlinx.android.synthetic.main.layout_product_item.view.*


class ProductsAdapter(private val context: Context,
                      private var productList: MutableList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = productList[position]
        holder.setData(current, position)
    }

    override fun getItemCount(): Int = productList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0
        private var current: Product? = null

        fun setData(current: Product?, position: Int) {

            current.let {

                itemView.tvTitle.text = current!!.name
                itemView.ivImg.setImageDrawable(ContextCompat.getDrawable(context, current.image))

                itemView.setOnClickListener {
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra("product", current)
                    context.startActivity(intent)
                }
            }

            this.pos = position
            this.current = current
        }
    }
}
