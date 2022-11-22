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
import android.widget.ArrayAdapter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class HomeFragment : Fragment() {

    private var threadList : MutableList<Thread> = mutableListOf()
    private var filterThreads:MutableList<Thread> = mutableListOf()

    private lateinit var binding: FragmentHomeBinding;
    private lateinit var threadAdapter: ThreadAdapter

    private val filterList  = arrayOf("Created At", "Views", "Vote", "Answer")
    private var selectedLocation:String = "";

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
        setDropdown()
        return binding.root;
    }
    private fun setDropdown(){
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.list_locations, filterList);
        binding.filter.setAdapter(adapter)
        binding.filter.setOnItemClickListener { adapterView, view, i, l ->
            selectedLocation = adapterView.getItemAtPosition(i).toString()
            when(selectedLocation){
                "Views" -> filterView()
                "Answer" -> filterAnswer()
                "Vote" -> filterVote()
                "Created At" -> filterAll()
            }
        }
    }

    private fun filterAll(){
        //        Thread List Bubble Sort with Answer
        for (pass in 0 until (threadList.size - 1)) {
            // A single pass of bubble sort
            for (currentPosition in 0 until (threadList.size - pass - 1)) {
                // This is a single step
                if (threadList[currentPosition].createdAt < threadList[currentPosition + 1].createdAt) {
                    val tmp : Thread = threadList[currentPosition]
                    threadList[currentPosition] = threadList[currentPosition + 1]
                    threadList[currentPosition + 1] = tmp
                }
            }
        }

        filterThreads = threadList;
        applyAdapter();
    }

    private fun filterAnswer(){
//        Thread List Bubble Sort with Answer
        for (pass in 0 until (threadList.size - 1)) {
            // A single pass of bubble sort
            for (currentPosition in 0 until (threadList.size - pass - 1)) {
                // This is a single step
                if (threadList[currentPosition].answer < threadList[currentPosition + 1].answer) {
                    val tmp : Thread = threadList[currentPosition]
                    threadList[currentPosition] = threadList[currentPosition + 1]
                    threadList[currentPosition + 1] = tmp
                }
            }
        }
        filterThreads = threadList;
        applyAdapter();
    }

    private fun filterVote(){
        for (pass in 0 until (threadList.size - 1)) {
            for (currentPosition in 0 until (threadList.size - pass - 1)) {
                if (threadList[currentPosition].topCount < threadList[currentPosition + 1].topCount) {
                    val tmp : Thread = threadList[currentPosition]
                    threadList[currentPosition] = threadList[currentPosition + 1]
                    threadList[currentPosition + 1] = tmp
                }
            }
        }
        filterThreads = threadList;
        applyAdapter();
    }

    private fun filterView(){
        for (pass in 0 until (threadList.size - 1)) {
            for (currentPosition in 0 until (threadList.size - pass - 1)) {
                if (threadList[currentPosition].topCount < threadList[currentPosition + 1].topCount) {
                    val tmp : Thread = threadList[currentPosition]
                    threadList[currentPosition] = threadList[currentPosition + 1]
                    threadList[currentPosition + 1] = tmp
                }
            }
        }
        filterThreads = threadList;
        applyAdapter();
    }

    private fun fetchThread(){

        val db = Firebase.firestore;
        db.collection("threads")
            .addSnapshotListener { value, e ->
                threadList.clear();

                if(e != null){
                    return@addSnapshotListener;
                }

                for (doc in value!!) {

//                  Get Thread Data
                    val title = doc.data["title"].toString()
                    val description = doc.data["description"].toString()
                    val user_id = doc.data["user_id"].toString()
                    val createdAt = doc.data["created_at"].toString();
                    val topCount = doc.data["top_count"].toString();
                    val downCount = doc.data["down_count"].toString();
                    val view = doc.data["view"].toString();
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
                            var tagName = doc.data!!["tag_name"].toString();
                            val user:User =User(userDoc.id, username, email, location, photoProfile, tagName = tagName)

//                           Add add getted data to the thread list (Vector)
                            val thread: Thread = Thread(uid =uid, title = title, desc = description, user_id = user_id, user = user, createdAt = createdAt, topCount = topCount.toInt(), downCount = downCount.toInt(), view = view.toInt());
                            thread.getAnswerCollection().addOnSuccessListener {
                                thread.answer =  it.size()
                                threadList.add(thread);
                                filterAll();
                            }
                        }
                    }
                }
            }
    }

    private fun applyAdapter(){
        threadAdapter = ThreadAdapter(filterThreads)
        binding.apply {
            rvHome.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = threadAdapter
            }
        }
    }
}