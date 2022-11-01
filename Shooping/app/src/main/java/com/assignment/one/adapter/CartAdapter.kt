package com.assignment.one.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.assignment.one.R
import com.assignment.one.activity.CartActivity
import com.assignment.one.model.Cart
import kotlinx.android.synthetic.main.layout_cart_item.view.*


class CartAdapter(private val context: Context,
                  private var cartList: MutableList<Cart>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_cart_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = cartList[position]
        holder.setData(current, position)
    }

    override fun getItemCount(): Int = cartList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0
        private var current: Cart? = null

        fun setData(current: Cart?, position: Int) {

            current.let {

                itemView.tvTitle.text = current!!.product.name
                itemView.tvDescription.text = current.product.description
                itemView.tvPrice.text = "$${current.product.price}"
                itemView.ivImg.setImageDrawable(ContextCompat.getDrawable(context, current.product.image))
                itemView.tvQuantity.text = "X${current.quantity}"

                itemView.btnClose.setOnClickListener {
                    (context as CartActivity).deleteItemFromCart(current.id)
                    cartList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

            this.pos = position
            this.current = current
        }
    }
}
