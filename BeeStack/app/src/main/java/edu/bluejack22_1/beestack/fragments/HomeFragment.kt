package edu.bluejack22_1.beestack.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack22_1.beestack.R

import ThreadAdapter
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
        val db = Firebase.firestore;
        db.collection("threads")
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
                    val uid = doc.id

//                  Then Get User Data
                    val docRef = db.collection("users").document(user_id);
                    docRef.addSnapshotListener { doc, error ->
                        if (doc != null) {
                            val username = doc.data!!["username"].toString()
                            val email = doc.data!!["email"].toString()
                            val location = doc.data!!["location"].toString()
                            val user:User =User(doc.id, username, email, location)

//                           Add add getted data to the thread list (Vector)
                            threadList.add(Thread(uid=uid, title = title, desc = description, user_id = user_id, user = user));
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