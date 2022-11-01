package com.assignment.one.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.assignment.one.R
import com.assignment.one.database.SQLiteDatabaseManager
import com.assignment.one.model.Product
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private val context = this@AddActivity

    private lateinit var product: Product

    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        initToolbar()
        getIntentData()
        setData()
        initListeners()
    }

    //Initialize toolbar by setting the title and the back button
    private fun initToolbar(){
        supportActionBar!!.title = "Add To Cart"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    //Get the product item sent from the prev activity
    private fun getIntentData(){
        product = intent.getSerializableExtra("product") as Product
    }

    //Set the product data to the layout
    private fun setData(){
        ivImg.setImageDrawable(ContextCompat.getDrawable(context, product.image))
        tvTitle.text = product.name
        tvPrice.text = "$${product.price}"
    }

    //Click listeners
    private fun initListeners(){
        btnIncrease.setOnClickListener {
            if(quantity < product.quantity){
                quantity+=1
                tvQuantity.text = quantity.toString()
                tvPrice.text = "$${(quantity * product.price)}"
            }
        }

        btnDecrease.setOnClickListener {
            if(quantity > 1){
                quantity -=1
                tvQuantity.text = quantity.toString()
                tvPrice.text = "$${(quantity * product.price)}"
            }
        }

        btnSubmitToCard.setOnClickListener {
            val sqLiteDatabaseManager = SQLiteDatabaseManager(context)
            sqLiteDatabaseManager.addToCart(product.id, quantity)
            finish()
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}