package edu.bluejack22_1.beestack.activities


import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityHomeBinding
import edu.bluejack22_1.beestack.fragments.*
import edu.bluejack22_1.beestack.model.User
import java.io.File
import kotlin.math.log


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

//    Setup toolbar after drawing maked!
    private fun setupToolbar(){

//      Change Action Toolbar
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_person_24)


//      Change Toolbar name based on user name
        binding.toolbarName.text = User.username

    }

    private fun setDrawingNavbar(){
        binding.navView.bringToFront()
        var drawer :DrawerLayout = binding.drawerLayout;
        val toggle = ActionBarDrawerToggle(this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        setupToolbar()
        setHeaderListener()
        setNavigationViewListener()
    }

    private fun setNavigationViewListener(){
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawing_nav_setting-> navigateProfilePage()
            }
            true
        }
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

    private fun setHeaderListener(){
        val headerView = binding.navView.getHeaderView(0)
        val viewProfile:TextView = headerView.findViewById(R.id.drawingViewProfile) as TextView
        val imageProfile:ImageView = headerView.findViewById(R.id.drawingImage) as ImageView

        viewProfile.setOnClickListener{
            navigateProfilePage()
        }
        Log.d("home-activity", User.photoProfileBitmap.toString())

        User.photoProfileListListener.add {
            if(User.photoProfileBitmap != null) {
                imageProfile.setImageBitmap(User.photoProfileBitmap)
            }
        }
    }

    override fun onResume() {
        if(User.photoProfileBitmap != null){
            setHeaderListener()
        }
        super.onResume()
    }

    private fun navigateSettingPage(){
        val i = Intent(this, ProfileActivity::class.java);
        startActivity(i)

    }

    private fun navigateProfilePage(){
        val i = Intent(this, ProfileActivity::class.java);
        startActivity(i)

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
