package edu.bluejack22_1.beestack.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityHomeBinding
import edu.bluejack22_1.beestack.facade.Helper
import edu.bluejack22_1.beestack.fragments.*
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.view.MyThread


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        validateUserExists()

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
        supportActionBar?.setTitle("");
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_clear_all_24)


//      Change Toolbar name based on user name
        binding.toolbarName.text = CurrentUser.username
        binding.toolbarImage.setImageBitmap(CurrentUser.photoProfileBitmap)



        CurrentUser.photoProfileListListener.add {
            if(CurrentUser.photoProfileBitmap != null) {
                binding.toolbarImage.setImageBitmap(CurrentUser.photoProfileBitmap)
            }
        }
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
                R.id.drawing_nav_my_thread-> MyThread.navigate(this);
                R.id.drawing_nav_team -> navigateTeamPage()
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
                R.id.tag ->replaceFragment(TagFragment())
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
        val userProfile:TextView = headerView.findViewById(R.id.drawingUsername) as TextView

        userProfile.text = CurrentUser.username

        viewProfile.setOnClickListener{
            navigateProfilePage()
        }

        if(CurrentUser.photoProfileBitmap != null){
            imageProfile.setImageBitmap(CurrentUser.photoProfileBitmap)
        }


        CurrentUser.photoProfileListListener.add {
            if(CurrentUser.photoProfileBitmap != null) {
                imageProfile.setImageBitmap(CurrentUser.photoProfileBitmap)
            }
        }
    }

    override fun onResume() {
        if(CurrentUser.photoProfileBitmap != null){
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
    private fun navigateTeamPage(){
        replaceFragment(TeamFragment())
        closeDrawer()
    }

    //Function to replace fragments
    public fun replaceFragment(fragment : Fragment){
        val fragmentTransaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null).replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit()
    }

    private fun closeDrawer(){
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
//      Close drawer if press back
        closeDrawer();
        super.onBackPressed()
    }

    private fun validateUserExists(){
        val firebase = FirebaseAuth.getInstance()
        if(firebase.currentUser == null){
            val i = Intent(this, MainActivity::class.java);
            finish();
            startActivity(i)
        }
    }
}
