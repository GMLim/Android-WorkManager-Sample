package gmlim.workmanager.sample.workmanagersample.workers

import android.util.Log
import androidx.work.Worker

class FilterWorker : Worker() {
    override fun doWork(): Result {
        Log.d("Sample", "FilterWorker Working...")

        return Result.SUCCESS
    }
}

class CompressWorker : Worker() {
    override fun doWork(): Result {
        Log.d("Sample", "CompressWorker Working...")

        return Result.SUCCESS
    }
}

class UploadWorker : Worker() {
    override fun doWork(): Result {
        Log.d("Sample", "UploadWorker Working...")

        return Result.SUCCESS
    }
}