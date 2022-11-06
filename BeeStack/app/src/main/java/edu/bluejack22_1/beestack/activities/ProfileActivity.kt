package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.bluejack22_1.beestack.databinding.ActivityProfileBinding

private lateinit var binding: ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProfileBinding.inflate(layoutInflater)
0
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}