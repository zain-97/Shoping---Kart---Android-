package com.assignment.one.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.one.R
import com.assignment.one.adapter.ProductsAdapter
import com.assignment.one.database.SQLiteDatabaseManager
import com.assignment.one.model.Product
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {

    private val context = this@ProductsActivity
    private lateinit var sqLiteManager: SQLiteDatabaseManager
    private var productList: MutableList<Product> = mutableListOf()
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        initDatabase()
        getProductsData()
        initRecyclerView()
    }

    //Initialize the database to use the object for any database operations
    private fun initDatabase(){
        sqLiteManager = SQLiteDatabaseManager(context)
    }

    //Get the products data from the database and add it in a list
    private fun getProductsData(){
        productList.addAll(sqLiteManager.getProducts())
    }

    //Initialize the recycler view set it's adapter and set the layout manager
    private fun initRecyclerView(){
        adapter = ProductsAdapter(context, productList)
        val gridLayoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(context, 2)
        rvProducts.layoutManager = gridLayoutManager
        rvProducts.adapter = adapter
    }

    //connect the main menu so we can show the cart icon in the toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //this gets called when one of the menu items is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_cart){
            startActivity(Intent(context, CartActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}