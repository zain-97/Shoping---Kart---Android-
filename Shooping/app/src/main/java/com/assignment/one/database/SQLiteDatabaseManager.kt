package com.assignment.one.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.assignment.one.model.Cart
import com.assignment.one.model.Product
import java.lang.String


class SQLiteDatabaseManager(context: Context) {

    //this helper variable created for the purpose of getting a writable database or readable databas
    //to do any action
    var helper: SQLHelper = SQLHelper(context)

    /*
    * insert product takes a product as a parameter and it inserts it into the products table
    * */

    fun insetProduct(product: Product): Long {
        val db = helper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Database.PRODUCTS_NAME, product.name)
        contentValues.put(Database.PRODUCTS_DESCRIPTION, product.description)
        contentValues.put(Database.PRODUCTS_PRICE, product.price)
        contentValues.put(Database.PRODUCTS_QUANTITY, product.quantity)
        contentValues.put(Database.PRODUCTS_IMAGE, product.image)
        val id = db.insert(Database.TABLE_PRODUCTS, null, contentValues)
        db.close()

        //-----------Will return -1 if not success --------//
        return id
    }

    /*
    * get products function this function get all the products from products table and
    * we add it in a mutable list and return it back as a return to the caller
    * */

    fun getProducts(): MutableList<Product> {
            val productList: MutableList<Product> = mutableListOf()
            val db = helper.readableDatabase
            val cursor =
                db.query(Database.TABLE_PRODUCTS, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val product = Product()
                product.id = cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_ID))
                product.name =
                    cursor.getString(cursor.getColumnIndex(Database.PRODUCTS_NAME))
                product.description =
                    cursor.getString(cursor.getColumnIndex(Database.PRODUCTS_DESCRIPTION))
                product.price =
                    cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_PRICE))
                product.quantity =
                    cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_QUANTITY))
                product.image =
                    cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_IMAGE))
                productList.add(product)
            }
            cursor.close()
            return productList
        }

    /*
    * Add to cart function adds a product to cart table. It takes 2 parameters the product id
    * and the quantity selected by the user and it inserts the item in the cart table
    * */
    fun addToCart(productId: Int, quantity: Int): Long {
        val db = helper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Database.CART_PRODUCT_ID, productId)
        contentValues.put(Database.CART_QUANTITY, quantity)
        val id = db.insert(Database.TABLE_CART, null, contentValues)
        db.close()

        //-----------Will return -1 if not success --------//
        return id
    }

    /*
    * get cart products function gets all the cart items in the table and also
    * find the product for every card and it gets it's data from the products table
    * */

    fun getCartProducts(): MutableList<Cart> {
        val cartList: MutableList<Cart> = mutableListOf()
        val db = helper.readableDatabase

        val cursor =
            db.query(Database.TABLE_CART, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val cart = Cart()
            cart.id = cursor.getInt(cursor.getColumnIndex(Database.CART_PRODUCT_ID))
            cart.productId =
                cursor.getInt(cursor.getColumnIndex(Database.CART_PRODUCT_ID))
            cart.quantity =
                cursor.getInt(cursor.getColumnIndex(Database.CART_QUANTITY))
            cart.product = getProduct(cart.productId)

            cartList.add(cart)
        }
        cursor.close()
        return cartList
    }

    /*
    * get product function finds only one product by id from the database
    * so it takes the product id as a parameter and it looks for then use the cursor to
    * get the product data in a model and return it back to the function caller as a return
    * */

    private fun getProduct(id: Int): Product {
        val db = helper.readableDatabase
        val args = arrayOf(id.toString())
        val cursor: Cursor = db.query(
            Database.TABLE_PRODUCTS,
            null,
            Database.PRODUCTS_ID + " = ? ",
            args,
            null,
            null,
            null
        )

        val product = Product()
        cursor.moveToNext()
        product.id = cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_ID))
        product.name =
            cursor.getString(cursor.getColumnIndex(Database.PRODUCTS_NAME))
        product.description =
            cursor.getString(cursor.getColumnIndex(Database.PRODUCTS_DESCRIPTION))
        product.price =
            cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_PRICE))
        product.quantity =
            cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_QUANTITY))
        product.image =
            cursor.getInt(cursor.getColumnIndex(Database.PRODUCTS_IMAGE))

        cursor.close()
        return product
    }

    /*
    * delete item from cart take the cart id and it looks for that item in the database
    * and it deletes it
    * */
    fun deleteItemFromCart(id: Int): Int{
        val db = helper.writableDatabase

        val args = arrayOf(String.valueOf(id))

        val count =
            db.delete(Database.TABLE_CART, Database.CART_ID + " = ? ", args)

        db.close()

        return count
    }

    /*
    * clear cart is for wiping all the cart table items
    * */

    fun clearCart(): Int{
        val db = helper.writableDatabase
        val count =
            db.delete(Database.TABLE_CART, null, null)
        db.close()
        return count
    }

    inner class SQLHelper(private var context: Context) : SQLiteOpenHelper(
        context, Database.databaseName, null, Database.databaseVersion
    ) {

        /*
        * This override function gets called first time when we do any action on the database
        * like inserting or retrieving any items
        * so here I'm creating the tables and inserting the products dummy data
        * */
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(Database.CREATE_TABLE_PRODUCTS)
            db.execSQL(Database.CREATE_TABLE_CART)

            Database.insertDummyProducts(context)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    }

}