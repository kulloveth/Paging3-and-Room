package dev.kulloveth.countriesandlanguages.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.kulloveth.countriesandlanguages.worker.SeedDatabaseWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Cal::class], version = 1)
@TypeConverters(LanguageConverter::class)
abstract class CalDatabase:RoomDatabase() {

abstract fun calDao():CalDao

companion object{
    @Volatile
    private var INSTANCE:CalDatabase? =null

    fun getDatabase(context: Context,coroutineScope: CoroutineScope): CalDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                CalDatabase::class.java,
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

   suspend fun insertData(context: Context,dao: CalDao){
       context.assets.open("data.json").bufferedReader().use { inputStream ->
           val cal = object : TypeToken<List<Cal>>() {}.type
           val calList: List<Cal> = Gson().fromJson(inputStream.readText(), cal)
           Log.e("seed", "$calList")
          dao.insertAll(calList)
       }
   }
}

}