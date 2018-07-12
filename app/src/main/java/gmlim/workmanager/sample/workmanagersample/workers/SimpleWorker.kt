package gmlim.workmanager.sample.workmanagersample.workers

import android.util.Log
import androidx.work.Worker

class SimpleWorker : Worker() {
    override fun doWork(): Result {
        Log.d("Sample", "SimpleWorker Working...")

        return Result.SUCCESS
    }

}