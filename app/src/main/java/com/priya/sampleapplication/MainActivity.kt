package com.priya.sampleapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf

class MainActivity : AppCompatActivity() {
    lateinit var db: DatabaseHelper
    lateinit var etName: EditText
    lateinit var etAge: EditText
    lateinit var btnSave: Button
    lateinit var btnShow: Button
    lateinit var btnDelete: Button
    lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        db = DatabaseHelper(this)
        // Views
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)

        btnSave = findViewById(R.id.btnSave)
        btnShow = findViewById(R.id.btnShow)
        btnDelete = findViewById(R.id.btnDelete)

        tvResult = findViewById(R.id.tvResult)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toInt()

            val success = db.insertStudent(name,age)
            if(success){
                Log.i("isCheVal","vale--->2")
                Toast.makeText(this,"Students Added", Toast.LENGTH_SHORT).show()

                etName.text.clear()
                etAge.text.clear()
            }
        }

        var student = Student("priya",110,"ECE")
        var studentList: ArrayList<Student> = ArrayList<Student>()
        studentList.add(student)
       /* var recyclerview = findViewById<RecyclerView>(R.id.student_Recycler)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = StudentAdapter(studentList)*/
        Log.i("isAdapterCheck","Check--->$studentList")
        val workManager = WorkManager.getInstance(this)
        val inputData = workDataOf("name" to "Android Developer")
        var workRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueue(workRequest)

        workManager
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(this){ workInfo ->
                when(workInfo?.state){
                    WorkInfo.State.ENQUEUED -> {
                        Log.d("WorkManager", "Work enqueued")
                    }

                    WorkInfo.State.RUNNING -> {
                        val progress =
                            workInfo.progress.getInt("progress", 0)

                        Log.d(
                            "WorkManager",
                            "Progress: $progress%"
                        )
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        val message =
                            workInfo.outputData
                                .getString("message")

                        Log.d(
                            "WorkManager",
                            "Success: $message"
                        )
                    }

                    WorkInfo.State.FAILED -> {
                        Log.d("WorkManager", "Work failed")
                    }

                    WorkInfo.State.CANCELLED -> {
                        Log.d("WorkManager", "Work cancelled")
                    }

                    else -> {}
                }
            }
    }
}