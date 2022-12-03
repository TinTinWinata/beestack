package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityChangePasswordBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.view.Home

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding;

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChangePasswordBinding.inflate(layoutInflater);
        changePasswordBtnListener();

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }



    private fun changePasswordBtnListener(){
        binding.changePasswordBtn.setOnClickListener {
            val lastPw = binding.lastPasswordET.text.toString();
            val newPw = binding.newPasswordET.text.toString();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(CurrentUser.email, lastPw).addOnSuccessListener {
                FirebaseAuth.getInstance().currentUser!!.updatePassword(newPw).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, getString(R.string.succesfully_change_password), Toast.LENGTH_SHORT).show();
                        Home.navigate(this);
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show();
                }
            }.addOnFailureListener {
                Toast.makeText(this, getString(R.string.last_password_must_same), Toast.LENGTH_SHORT).show();
            };
        }
    }

}