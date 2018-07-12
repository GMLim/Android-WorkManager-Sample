package gmlim.workmanager.sample.workmanagersample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.work.*
import gmlim.workmanager.sample.workmanagersample.workers.CompressWorker
import gmlim.workmanager.sample.workmanagersample.workers.FilterWorker
import gmlim.workmanager.sample.workmanagersample.workers.SimpleWorker
import gmlim.workmanager.sample.workmanagersample.workers.UploadWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btRunOneTime.setOnClickListener {
            doWorkOneTime()
        }

        btRunPeriodic.setOnClickListener {
            doWorkPeriodic()
        }

        btRunOneTimeWithConstraints.setOnClickListener {
            doWorkWithConstraints()
        }

        btRunChaningWork.setOnClickListener {
            doWorkChaining()
        }

        btRunChaningWork2.setOnClickListener {
            doWorkChaining2()
        }
    }

    private fun doWorkOneTime() {
        val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>().build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(workRequest)
    }

    private fun doWorkPeriodic() {
        val workRequest = PeriodicWorkRequestBuilder<SimpleWorker>(15, TimeUnit.MINUTES).build()
        PeriodicWorkRequest.Builder(SimpleWorker::class.java, 15, TimeUnit.MINUTES).build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(workRequest)
    }

    private fun doWorkWithConstraints() {
        // 네트워크 연결 상태 와 충전 중 인 상태를 제약조건으로 추가 한다
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build()

        // 제약 조건과 함께 작업 요청 생성
        val requestConstraint  = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setConstraints(constraints)
                .build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(requestConstraint)
    }

    private fun doWorkChaining() {
        val compressWork = OneTimeWorkRequestBuilder<CompressWorker>().build()
        val uploadWork = OneTimeWorkRequestBuilder<UploadWorker>().build()

        WorkManager.getInstance()?.apply {
            beginWith(compressWork).then(uploadWork).enqueue()
        }
    }

    private fun doWorkChaining2() {
        // 세개의 필터적용 작업 요청 생성
        val filterWork1 = OneTimeWorkRequestBuilder<FilterWorker>().build()
        val filterWork2 = OneTimeWorkRequestBuilder<FilterWorker>().build()
        val filterWork3 = OneTimeWorkRequestBuilder<FilterWorker>().build()

        val compressWork = OneTimeWorkRequestBuilder<CompressWorker>().build()
        val uploadWork = OneTimeWorkRequestBuilder<UploadWorker>().build()

        WorkManager.getInstance()?.apply {
            beginWith(filterWork1, filterWork2, filterWork3).then(compressWork).then(uploadWork).enqueue()
        }
    }
}
