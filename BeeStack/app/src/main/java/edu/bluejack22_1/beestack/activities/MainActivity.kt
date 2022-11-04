package edu.bluejack22_1.beestack.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.adapters.ViewPagerAdapter
import me.relex.circleindicator.CircleIndicator3

class MainActivity : AppCompatActivity() {

    

    override fun onCreate(savedInstanceState: Bundle?) {
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