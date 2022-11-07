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

        val db = Firebase.firestore;

        binding = FragmentHomeBinding.inflate(layoutInflater)

        db.collection("threads")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc_thread in value!!) {

                    // Get Thread Details
                    val title= doc_thread.data["title"].toString()
                    val description = doc_thread.data["description"].toString()
                    val user_id = doc_thread.data["user_id"].toString()

                    // Get User Data
                    val docRef = db.collection("users").document(user_id);
                    docRef.addSnapshotListener { doc, error ->
                        if (doc != null) {
                            val owner = doc.data!!["username"].toString()

                            // Create a new Thread Object
                            val thread:Thread = Thread(title,description,user_id,owner)

                            // Add thread id
                            thread.uid = doc_thread.id;

                            // Add thread to list
                            threadList.add(thread);
                            applyAdapter();
                        }
                    }
                }
            }
        return binding.root;
    }

    fun applyAdapter(){
        threadAdapter = ThreadAdapter(threadList)
        binding.apply {
            rvHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = threadAdapter
            }
        }
    }
}