package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityCreateTagBinding
import edu.bluejack22_1.beestack.model.Tag

class CreateTagActivity : AppCompatActivity() {

    private lateinit var  binding :ActivityCreateTagBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTagBinding.inflate(layoutInflater)
        setToolbar()
        validateAndSetName()
        createListener();
        setContentView(binding.root);
    }

    private fun validateAndSetName(){
        val name :String?= intent.getStringExtra("name")
        if(name != null){
            binding.nameET.setText(name);
        }
    }

    private fun createListener(){
        binding.createBtn.setOnClickListener {
            val name:String = binding.nameET.text.toString()
            val description:String = binding.descriptionET.text.toString()
            if(name.isNotEmpty() && description.isNotEmpty())
            {
                val db = Firebase.firestore;
                db.collection("tags")
                    .add(Tag(name=name, description = description).getHashMap())
                        .addOnSuccessListener {
                            binding.nameET.setText("")
                            binding.descriptionET.setText("")
                            Toast.makeText(this, "Succesfully Create Tag", Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                        }
            }else{
                Toast.makeText(this, "You need validates all empty space", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.pageName.setText("Create Tag");
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowTitleEnabled(false);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }
}