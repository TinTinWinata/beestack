package edu.bluejack22_1.beestack.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.model.CurrentUser

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val isExists :Boolean = isAuthExists();
        Handler().postDelayed({
            if(isExists){
                navigateHome()
            }else{
                navigateMain();
            }
        }, 3000)

    }

    private fun isAuthExists() : Boolean{
        val firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.currentUser != null){
            CurrentUser.login(firebaseAuth.currentUser!!.uid)
            return true;
        }
        return false;
    }

    private fun navigateHome(){
        //     Navigate to home
        val intent = Intent(this, HomeActivity::class.java);
        startActivity(intent);
    }

    private fun navigateMain(){
        val i = Intent(this, MainActivity::class.java);
        finish()
        startActivity(i)
    }
}