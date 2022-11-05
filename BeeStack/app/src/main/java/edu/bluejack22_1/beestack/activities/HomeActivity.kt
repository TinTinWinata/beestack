package edu.bluejack22_1.beestack.activities

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityHomeBinding
import edu.bluejack22_1.beestack.fragments.*

class HomeActivity : AppCompatActivity() {
    val db = Firebase.firestore;

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setBottomNavbar()
        replaceFragment(HomeFragment()) // Initial Fragment


        setContentView(binding.getRoot());


        val thread = hashMapOf(
            "title" to "Test Title Okay"    ,
            "description" to "Testing Pertama kali yo",
        )

        db.collection("threads")
            .add(thread)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
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

    private fun replaceFragment(fragment : Fragment){
        val fragmentTransaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit()
    }
}
