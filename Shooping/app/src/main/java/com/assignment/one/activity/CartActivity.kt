package com.assignment.one.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.one.R
import com.assignment.one.adapter.CartAdapter
import com.assignment.one.database.SQLiteDatabaseManager
import com.assignment.one.model.Cart
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private val context = this@CartActivity
    private lateinit var sqLiteManager: SQLiteDatabaseManager
    private var cartList: MutableList<Cart> = mutableListOf()
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        initToolbar()
        getCartProducts()
        initRecyclerView()
    }

    //Initialize toolbar by setting the title and the back button
    private fun initToolbar(){
        supportActionBar!!.title = "Cart"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    //Get cart products from the database and add it to the cart list so we can show it to user
    private fun getCartProducts(){
        sqLiteManager = SQLiteDatabaseManager(context)
        cartList.addAll(sqLiteManager.getCartProducts())
    }

    //Initialize the recycler view set it's adapter and set the layout manager also setting the total cost
    private fun initRecyclerView(){
        adapter = CartAdapter(context, cartList)
        val linearLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context)
        rvProducts.layoutManager = linearLayoutManager
        rvProducts.adapter = adapter

        var totalCost = 0
        for(cart in cartList){
            totalCost+= (cart.quantity * cart.product.price)
        }

        tvTotalCost.text = "Total Cost: $$totalCost"

        if(cartList.isEmpty()){
            tvTotalCost.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(cartList.isNotEmpty())
            menuInflater.inflate(R.menu.cart, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                finish()
            }

            R.id.action_checkout -> {
                clearCart()
                Toast.makeText(context, "Purchase is complete", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun deleteItemFromCart(cartId: Int){
        sqLiteManager.deleteItemFromCart(cartId)
    }

    private fun clearCart(){
        sqLiteManager.clearCart()
    }

}