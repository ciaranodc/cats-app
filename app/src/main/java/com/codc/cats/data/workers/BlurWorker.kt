package com.codc.cats.data.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}