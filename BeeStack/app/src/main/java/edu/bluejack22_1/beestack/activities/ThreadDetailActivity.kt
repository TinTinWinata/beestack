package edu.bluejack22_1.beestack.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.model.Thread

class ThreadDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_detail)

        val thread : Thread= intent.getSerializableExtra("object") as Thread
        Log.d("test", thread.title)
    }
}