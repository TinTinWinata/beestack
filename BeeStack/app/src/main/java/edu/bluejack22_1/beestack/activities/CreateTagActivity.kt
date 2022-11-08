package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityCreateTagBinding
import edu.bluejack22_1.beestack.model.Tag

class CreateTagActivity : AppCompatActivity() {

    private lateinit var  binding :ActivityCreateTagBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTagBinding.inflate(layoutInflater)
        setToolbar()
        setContentView(binding.root);
    }

    fun createListener(){
        binding.createBtn.setOnClickListener {
            val name:String = binding.nameET.text.toString()
            val description:String = binding.descriptionET.text.toString()
//            if(name.isEmpty() || description.isEmpty())
//            Tag()
        }
    }

    fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}