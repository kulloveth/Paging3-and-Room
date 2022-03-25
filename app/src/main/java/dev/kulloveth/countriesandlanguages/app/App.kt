package dev.kulloveth.countriesandlanguages.app

import android.app.Application
import dev.kulloveth.countriesandlanguages.data.Repository
import dev.kulloveth.countriesandlanguages.data.db.CalDatabase
import kotlinx.coroutines.GlobalScope

class App:Application() {

     private val db by lazy {
        CalDatabase.getDatabase(this,AppScope)
    }

    val repository by lazy {
        Repository(db.calDao())
    }


}