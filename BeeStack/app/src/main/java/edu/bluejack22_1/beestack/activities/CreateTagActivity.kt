package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityCreateTagBinding

class CreateTagActivity : AppCompatActivity() {

    private lateinit var  binding :ActivityCreateTagBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTagBinding.inflate(layoutInflater)
        setContentView(binding.root);
    }
}