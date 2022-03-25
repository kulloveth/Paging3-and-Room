package dev.kulloveth.countriesandlanguages.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.kulloveth.countriesandlanguages.data.db.Cal
import dev.kulloveth.countriesandlanguages.data.db.CalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeedDatabaseWork(context: Context,
                       workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).bufferedReader().use { inputStream ->
                        val cal = object : TypeToken<List<Cal>>() {}.type
                        val calList: List<Cal> = Gson().fromJson(inputStream.readText(), cal)
                       // Log.e(TAG, "$calList")
                        Result.success()
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "CAL_FILE_NAME"
    }
}