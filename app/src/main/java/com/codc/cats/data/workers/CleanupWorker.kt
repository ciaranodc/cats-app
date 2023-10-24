package com.codc.cats.data.workers

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.codc.cats.data.common.OUTPUT_PATH
import com.codc.cats.data.common.isImageFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File


class CleanupWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Makes a notification when the work starts and slows down the work so that
        // it's easier to see each WorkRequest start
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Cleaning up old temporary files", Toast.LENGTH_SHORT).show()
        }
        Thread.sleep(3000)

        return withContext(Dispatchers.IO) {
            try {
                cleanupCache()
                Result.success()
            } catch (exception: Exception) {
                Timber.e(exception)
                Result.failure()
            }
        }
    }

    private fun cleanupCache() {
        File(applicationContext.cacheDir, OUTPUT_PATH).apply {
            Timber.d("Output dir set: $absolutePath")
            if (exists()) {
                listFiles()?.forEach { file ->
                    if (file.name.isImageFile()) {
                        val deleted = file.delete()
                        Timber.i("Deleted ${file.name} - $deleted")
                    }
                }
            }
        }
    }
}