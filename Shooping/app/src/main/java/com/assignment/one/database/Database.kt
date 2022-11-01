package com.assignment.one.database

import android.content.Context
import com.assignment.one.R
import com.assignment.one.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
This class for all the database constants.
This is object class so we can easily call these constants from any where in the app
 */

object Database {
    const val databaseName = "appDatabase"
    const val databaseVersion = 1

    //==========================Products==========================//

    const val TABLE_PRODUCTS = "products"
    const val PRODUCTS_ID = "id"
    const val PRODUCTS_NAME = "name"
    const val PRODUCTS_PRICE = "price"
    const val PRODUCTS_QUANTITY = "quantity"
    const val PRODUCTS_DESCRIPTION = "description"
    const val PRODUCTS_IMAGE = "image"
    const val CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
            PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PRODUCTS_NAME + " VARCHAR(250), " +
            PRODUCTS_PRICE + " INTEGER, " +
            PRODUCTS_QUANTITY + " INTEGER, " +
            PRODUCTS_DESCRIPTION + " TEXT, " +
            PRODUCTS_IMAGE + " INTEGER);"

    //==========================cart==========================//

    const val TABLE_CART = "cart"
    const val CART_ID = "id"
    const val CART_QUANTITY = "quantity"
    const val CART_PRODUCT_ID = "product_id"
    const val CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + " (" +
            CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CART_QUANTITY + " INTEGER, " +
            CART_PRODUCT_ID + " INTEGER)"

    fun insertDummyProducts(context: Context){
        CoroutineScope(Dispatchers.Default).launch {
            val sqLiteDatabaseManager = SQLiteDatabaseManager(context)

            val product1 = Product()
            product1.name = "Iphone XS"
            product1.price = 800
            product1.quantity = 5
            product1.description = "Apple iPhone XS mobile was launched in September 2018. The phone comes with a 5.80-inch touchscreen display with a resolution of 1125x2436 pixels at a pixel density of 458 pixels per inch (ppi). Apple iPhone XS is powered by a hexa-core Apple A12 Bionic processor. The Apple iPhone XS supports wireless charging, as well as proprietary fast charging."
            product1.image = R.drawable.iphonexs
            sqLiteDatabaseManager.insetProduct(product1)

            val product2 = Product()
            product2.name = "Iphone 11"
            product2.price = 1000
            product2.quantity = 2
            product2.description = "The iPhone 11 is a smartphone designed, developed, and marketed by Apple Inc. It is the 13th generation, lower-priced iPhone, succeeding the iPhone XR. It was unveiled on September 10, 2019, alongside the higher-end iPhone 11 Pro flagship at the Steve Jobs Theater in Apple Park, Cupertino, by Apple CEO Tim Cook. Preorders began on September 13, 2019, and the phone was officially released on September 20, 2019, one day after the official public release of iOS 13.\n" +
                    "\n" +
                    "The prominent changes compared with the iPhone XR are the Apple A13 Bionic chip, and an ultra-wide dual-camera system.[4] While the iPhone 11 Pro comes with an 18 W Lightning to USB-C fast charger, the iPhone 11, until October 2020, came with the same 5 W charger found on previous iPhones, though the 18 W charger is compatible with both models.[5][6] "
            product2.image = R.drawable.iphone11
            sqLiteDatabaseManager.insetProduct(product2)

            val product3 = Product()
            product3.name = "Macbook Pro"
            product3.price = 2400
            product3.quantity = 6
            product3.description = "Apple MacBook Pro is a macOS laptop with a 13.30-inch display that has a resolution of 2560x1600 pixels. It is powered by a Core i5 processor and it comes with 12GB of RAM. The Apple MacBook Pro packs 512GB of SSD storage.\n" +
                    "\n" +
                    "Connectivity options include Wi-Fi 802.11 ac, Bluetooth and it comes with 2 USB ports (2 x USB 3.0), Mic In ports.\n" +
                    "\n" +
                    "As of 1st November 2021, Apple MacBook Pro price in India starts at Rs. 159,900."
            product3.image = R.drawable.macbookbro
            sqLiteDatabaseManager.insetProduct(product3)
        }

    }
}