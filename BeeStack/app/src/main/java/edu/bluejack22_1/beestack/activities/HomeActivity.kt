package edu.bluejack22_1.beestack.activities


import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityHomeBinding
import edu.bluejack22_1.beestack.fragments.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setToolbar()
        setDrawingNavbar()
        setBottomNavbar()

        replaceFragment(HomeFragment()) // Initial Fragment

        setContentView(binding.getRoot());
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setDrawingNavbar(){
        var drawer :DrawerLayout = binding.drawerLayout;
        val toggle = ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_person_24)
    }


    private fun setBottomNavbar(){
        binding.btmNav.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED;
        binding.btmNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.insert ->  replaceFragment(InsertFragment())
                R.id.team ->replaceFragment(TeamFragment())
                R.id.notification->replaceFragment(NotificationFragment())
            }

//          Listener needs to Return True
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentTransaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
//      Close drawer if press back
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}
