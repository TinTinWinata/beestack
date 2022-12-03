package edu.bluejack22_1.beestack.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import ThreadAdapter
import android.widget.ArrayAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.FragmentSearchBinding
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class SearchFragment : Fragment() {

    private var threadList : MutableList<Thread> = mutableListOf()
    private var filterThreads:MutableList<Thread> = mutableListOf()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val filterList  = arrayOf("Created At", "Views", "Vote", "Answer")
    private var selectedLocation:String = "";

    private lateinit var threadAdapter: ThreadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false);


        fetchDefaultThread();
        setSearchListener()
        setDropdown()

        return binding.root;
    }

    private fun filterAll(){
        //        Thread List Bubble Sort with Created At
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
        applyAdapter();
    }

    private fun filterView(){
        for (pass in 0 until (threadList.size - 1)) {
            for (currentPosition in 0 until (threadList.size - pass - 1)) {
                if (threadList[currentPosition].view < threadList[currentPosition + 1].view) {
                    val tmp : Thread = threadList[currentPosition]
                    threadList[currentPosition] = threadList[currentPosition + 1]
                    threadList[currentPosition + 1] = tmp
                }
            }
        }
        applyAdapter();
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


    private fun hideProgressBar(){
        binding.loadingLayout.visibility = View.GONE
    }

    private fun playProgressBar(){
        binding.loadingLayout.visibility = View.VISIBLE
    }

    private fun setSearchListener(){
        binding.searchBtn.setOnClickListener {
            fetchThread(binding.searchET.text.toString())
        }

    }

    private fun fetchThread(str: String){
            playProgressBar()
            threadList.clear();
            if(str == "" || str.isEmpty()){
                fetchDefaultThread()
                return;
            }
            val db = Firebase.firestore;
            db.collection("threads")
                .orderBy("title")
                .startAt(str)
                .endAt(str+"\uf8ff")
                .get()
                .addOnSuccessListener{ value ->
                    if(value.isEmpty){
                        threadList.clear();
                        applyAdapter();
                        Log.d("test", "Done with no value")
                    }else{
                        Log.d("test", "Done with value")
                    }


                    for (doc in value!!) {
//                  Get Thread Data
                        val title = doc.data["title"].toString()
                        val description = doc.data["description"].toString()
                        val user_id = doc.data["user_id"].toString()
                        val uid = doc.id
                        val createdAt = doc.data["created_at"].toString();
                        val topCount = doc.data["top_count"].toString();
                        val downCount = doc.data["down_count"].toString();


//                  Then Get User Data
                        val docRef = db.collection("users").document(user_id);
                        docRef.get()
                            .addOnSuccessListener { doc ->
                            if (doc != null) {
                                val username = doc.data!!["username"].toString()
                                val email = doc.data!!["email"].toString()
                                val location = doc.data!!["location"].toString()
                                val url = doc.data!!["photo_profile_url"].toString();
                                var tagName = doc.data!!["tag_name"].toString();
                                val user:User =User(uid = doc.id, username = username, email = email, location = location, photoProfile = url, tagName = tagName)

//                           Add add getted data to the thread list (Vector)
                                threadList.add(Thread(uid =uid, title = title, desc = description, user_id = user_id, user = user, createdAt = createdAt, topCount = topCount.toInt(), downCount = downCount.toInt()));
                                filterAll();
                            }
                        }
                    }

                }.addOnFailureListener {
                    Log.d("test", "Fail!")
                }

    }

    private fun fetchDefaultThread(){
        playProgressBar()

        val db = Firebase.firestore;
        db.collection("threads")
            .get().addOnSuccessListener { value ->

                if(value.isEmpty)
                {
                    applyAdapter()
                }

                for (doc in value!!) {
                    Log.d("test", value!!.size().toString());
//                  Get Thread Data
                    val title = doc.data["title"].toString()
                    val description = doc.data["description"].toString()
                    val user_id = doc.data["user_id"].toString()
                    val uid = doc.id
                    val createdAt = doc.data["created_at"].toString();
                    val topCount = doc.data["top_count"].toString();
                    val downCount = doc.data["down_count"].toString();

//                  Then Get User Data
                    val docRef = db.collection("users").document(user_id);
                    docRef.get().addOnSuccessListener { doc ->
                        if (doc != null) {
                            val username = doc.data!!["username"].toString()
                            val email = doc.data!!["email"].toString()
                            val location = doc.data!!["location"].toString()
                            val url = doc.data!!["photo_profile_url"].toString();
                            var tagName = doc.data!!["tag_name"].toString();
                            val user:User =User(doc.id, username, email, location, url, tagName = tagName);

//                           Add add getted data to the thread list (Vector)
                            Log.d("test", "Thread : " + title);
                            threadList.add(Thread(uid =uid, title = title, desc = description, user_id = user_id, user = user, createdAt = createdAt, topCount = topCount.toInt(), downCount = downCount.toInt()));
                            filterAll();
                        }
                    }
                }

            }
    }


    private fun applyAdapter(){
        hideProgressBar()
        threadAdapter = ThreadAdapter(threadList)
        binding.apply {
            threadRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = threadAdapter
            }
        }
    }
}