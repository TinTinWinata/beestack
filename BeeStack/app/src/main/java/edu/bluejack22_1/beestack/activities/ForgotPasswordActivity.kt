package edu.bluejack22_1.beestack.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityForgotPasswordBinding


class ForgotPasswordActivity : AppCompatActivity() {


    private lateinit var binding: ActivityForgotPasswordBinding;
    private lateinit var firebaseAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance();
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root);

        backListener()
        sendForgetListener()
    }

    private fun backListener(){
        binding.backBtn.setOnClickListener{
            val mainIntent = Intent(this, MainActivity::class.java);
            startActivity(mainIntent)
        }
    }

    private fun sendForgetListener(){


//        Listener
        binding.forgetPasswordBtn.setOnClickListener{


            //        Get Email
            val email = binding.forgetEmailET.text.toString();



            if(email.isEmpty()){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show()
            }else{
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Email Sended", Toast.LENGTH_LONG).show();
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}