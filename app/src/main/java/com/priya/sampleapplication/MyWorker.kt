package com.priya.sampleapplication

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class MyWorker(appContext: Context,
               workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val name = inputData.getString("name") ?: "User"
        for(i in 1..5){
            delay(1000)
            setProgress(workDataOf("progress" to (i * 20)))
        }
        Log.d("MyWorker", "Hello $name! Work completed.")

        // Return success
        return Result.success(
            workDataOf("message" to "Work completed successfully")
        )
    }
}