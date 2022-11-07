package edu.bluejack22_1.beestack.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityThreadDetailBinding
import edu.bluejack22_1.beestack.model.Thread

class ThreadDetailActivity : AppCompatActivity() {

    private  lateinit var binding :ActivityThreadDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThreadDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread : Thread= intent.getSerializableExtra("thread") as Thread
    }
}