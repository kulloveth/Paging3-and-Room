package dev.kulloveth.countriesandlanguages.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Cal::class], version = 1)
@TypeConverters(LanguageConverter::class)
abstract class CountryDatabase:RoomDatabase() {

abstract fun calDao():CountryDao

companion object{
    @Volatile
    private var INSTANCE:CountryDatabase? =null

    fun getDatabase(context: Context,coroutineScope: CoroutineScope): CountryDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                CountryDatabase::class.java,
                "cal_database")
                .addCallback(object :RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        coroutineScope.launch {
                            INSTANCE?.let {
                                insertData(context, it.calDao())
                            }
                        }
                    }
                })
                .build()
            INSTANCE = instance

            instance
        }
    }

   suspend fun insertData(context: Context,dao: CountryDao){
       context.assets.open("data.json").bufferedReader().use { inputStream ->
           val cal = object : TypeToken<List<Cal>>() {}.type
           val calList: List<Cal> = Gson().fromJson(inputStream.readText(), cal)
           Log.e("seed", "$calList")
          dao.insertAll(calList)
       }
   }
}

}