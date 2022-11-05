package edu.bluejack22_1.beestack.activities

import ThreadAdapter
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityHomeBinding
import edu.bluejack22_1.beestack.model.Thread

class HomeActivity : AppCompatActivity() {
    val db = Firebase.firestore;

    private lateinit var binding: ActivityHomeBinding

    private var threadList : MutableList<Thread> = mutableListOf()
    private lateinit var threadAdapter: ThreadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root);
        dummyData();

        threadAdapter = ThreadAdapter(threadList)
        binding.apply {
            rvHome.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = threadAdapter
            }
        }


        val thread = hashMapOf(
            "title" to "T2",
            "description" to "Testing Pertama kali yo",
        )

//        db.collection("threads")
//            .add(thread)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

        db.collection("threads")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val cities = ArrayList<String>()
                for (doc in value!!) {
                    doc.getString("title")?.let {
                        cities.add(it)
                    }
                }
                Log.d(TAG, "Current cites in CA: $cities")
            }


    }

    fun dummyData(){
        threadList.add(Thread("Title 1","Sample 1"))
        threadList.add(Thread("Title 2","Sample 1"))
        threadList.add(Thread("Title 3","Sample 1"))
        threadList.add(Thread("Title 4","Sample 1"))
    }
}