package edu.bluejack22_1.beestack.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.adapters.ViewPagerAdapter
import me.relex.circleindicator.CircleIndicator3

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {

//      Hide Action Bar if exists in this Activity
        supportActionBar!!.hide();

//      If there's any user in sign in, then go to login

        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, HomeActivity::class.java);
//            startActivity(intent);
//        }

//      ------------------------------------

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      Make view pager works

        val viewPager : ViewPager2 = findViewById<ViewPager2>(R.id.viewPager);
        val circleIndicator : CircleIndicator3 = findViewById<CircleIndicator3>(R.id.circleIndicator);
        val adapter : ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle);
        viewPager.adapter = adapter;
        circleIndicator.setViewPager(viewPager);

//      -----------------------------------
    }
}