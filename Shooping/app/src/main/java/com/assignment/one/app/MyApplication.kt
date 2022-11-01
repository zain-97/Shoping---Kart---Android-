package com.assignment.one.app

import android.app.Application
import com.assignment.one.database.SQLiteDatabaseManager

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        val sqLiteDatabaseManager = SQLiteDatabaseManager(this)
        sqLiteDatabaseManager.getProducts()
    }

}