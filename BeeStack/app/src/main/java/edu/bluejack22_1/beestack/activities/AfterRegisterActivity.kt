package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.bluejack22_1.beestack.databinding.ActivityAfterRegisterBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.view.Home

class AfterRegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAfterRegisterBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterRegisterBinding.inflate(layoutInflater);
        setListener();
        setContentView(binding.root);
    }

    private fun setListener(){
        binding.submitBtn.setOnClickListener {
            val name : String = binding.name.text.toString();
            if(name.isNotEmpty()){
                CurrentUser.username = name;
                CurrentUser.update().addOnSuccessListener {
                    Home.navigate(this);
                }
            }else{
                Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

}