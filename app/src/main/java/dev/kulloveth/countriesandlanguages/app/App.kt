package dev.kulloveth.countriesandlanguages.app

import android.app.Application
import dev.kulloveth.countriesandlanguages.data.Repository
import dev.kulloveth.countriesandlanguages.data.db.CountryDatabase

class App:Application() {

     private val db by lazy {
        CountryDatabase.getDatabase(this,AppScope)
    }

    val repository by lazy {
        Repository(db.calDao())
    }


}