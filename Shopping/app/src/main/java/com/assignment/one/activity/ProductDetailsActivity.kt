package com.assignment.one.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.assignment.one.R
import com.assignment.one.model.Product
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : AppCompatActivity() {

    private val context = this@ProductDetailsActivity

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        initToolbar()
        getIntentData()
        setData()
        initListeners()
    }

    //Get the product item sent from the prev activity
    private fun getIntentData(){
        product = intent.getSerializableExtra("product") as Product
    }

    //Initialize toolbar by setting the title and the back button
    private fun initToolbar(){
        supportActionBar!!.title = "View Product"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    //Set the product data to the layout
    private fun setData(){
        ivImg.setImageDrawable(ContextCompat.getDrawable(context, product.image))
        tvProductName.text = product.name
        tvProductDescription.text = product.description
        tvPrice.text = "$${product.price}"
        tvQuantity.text = "Available ${product.quantity} items"
    }

    //Click listeners
    private fun initListeners(){
        fabAddToCart.setOnClickListener {
            val intent = Intent(context, AddActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}