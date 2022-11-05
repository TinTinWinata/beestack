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
import edu.bluejack22_1.beestack.model.Thread
import ThreadAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding


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
        dummyData();

        binding = FragmentHomeBinding.inflate(layoutInflater)

        threadAdapter = ThreadAdapter(threadList)
        binding.apply {
            rvHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = threadAdapter
            }
        }

        db.collection("threads")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val cities = ArrayList<String>()
                for (doc in value!!) {
                    doc.getString("title")?.let {
                        cities.add(it)
                    }
                }
                Log.d(ContentValues.TAG, "Current cites in CA: $cities")
            }


        val thread = hashMapOf(
            "title" to "T2",
            "description" to "Testing Pertama kali yo",
        )

        return binding.root;
    }

    fun dummyData(){
        threadList.add(Thread("Title 1","Sample 1"))
        threadList.add(Thread("Title 2","Sample 1"))
        threadList.add(Thread("Title 3","Sample 1"))
        threadList.add(Thread("Title 4","Sample 1"))
    }

}