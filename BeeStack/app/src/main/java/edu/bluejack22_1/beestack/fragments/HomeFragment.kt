package edu.bluejack22_1.beestack.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import ThreadAdapter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class HomeFragment : Fragment() {

    private var threadList : MutableList<Thread> = mutableListOf()
    private lateinit var binding: FragmentHomeBinding;
    private lateinit var threadAdapter: ThreadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        fetchThread();
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root;
    }
    private fun fetchThread(){
        threadList.clear();
        val db = Firebase.firestore;
        db.collection("threads")
            .orderBy("created_at", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {

//                  Get Thread Data
                    val title = doc.data["title"].toString()
                    val description = doc.data["description"].toString()
                    val user_id = doc.data["user_id"].toString()
                    val createdAt = doc.data["created_at"].toString();
                    val topCount = doc.data["top_count"].toString();
                    val downCount = doc.data["down_count"].toString();

                    val uid = doc.id


//                  Then Get User Data
                    val docRef = db.collection("users").document(user_id);

                    docRef.get()
                            .addOnSuccessListener { userDoc ->

                        if (userDoc != null) {
                            val username = userDoc.data!!["username"].toString()
                            val email = userDoc.data!!["email"].toString()
                            val location = userDoc.data!!["location"].toString()
                            val photoProfile = userDoc.data!!["photo_profile_url"].toString();
                            val user:User =User(userDoc.id, username, email, location, photoProfile)

//                           Add add getted data to the thread list (Vector)
                            threadList.add(Thread(uid =uid, title = title, desc = description, user_id = user_id, user = user, createdAt = createdAt, topCount = topCount.toInt(), downCount = downCount.toInt()));
                            applyAdapter();
                        }
                    }
                }
            }
    }

    private fun applyAdapter(){
        threadAdapter = ThreadAdapter(threadList)
        binding.apply {
            rvHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = threadAdapter
            }
        }
    }
}