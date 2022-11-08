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
import androidx.core.widget.addTextChangedListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding
import edu.bluejack22_1.beestack.databinding.FragmentLoginBinding
import edu.bluejack22_1.beestack.databinding.FragmentSearchBinding
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class SearchFragment : Fragment() {

    private var threadList : MutableList<Thread> = mutableListOf()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        return binding.root;
    }


    private fun hideProgressBar(){
        binding.loadingLayout.visibility = View.GONE
    }

    private fun playProgressBar(){
        binding.loadingLayout.visibility = View.VISIBLE
    }

    private fun setSearchListener(){

//        Search Text Listener Bug
//        binding.searchET.addTextChangedListener {
//            Log.d("text", "searching ... " + it.toString())
//            fetchThread(it.toString())
//        }

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
            Log.d("test", "Fetching ...")
            db.collection("threads")
                .orderBy("title")
                .startAt(str)
                .endAt(str+"\uf8ff")
                .get()
                .addOnSuccessListener{ value ->
                    if(value.isEmpty){
                        applyAdapter()
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

//                  Then Get User Data
                        val docRef = db.collection("users").document(user_id);
                        docRef.get()
                            .addOnSuccessListener { doc ->
                            if (doc != null) {
                                val username = doc.data!!["username"].toString()
                                val email = doc.data!!["email"].toString()
                                val location = doc.data!!["location"].toString()
                                val user:User =User(doc.id, username, email, location)

//                           Add add getted data to the thread list (Vector)
                                threadList.add(Thread(uid=uid, title = title, desc = description, user_id = user_id, user = user));
                                applyAdapter()
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

//                  Get Thread Data
                    val title = doc.data["title"].toString()
                    val description = doc.data["description"].toString()
                    val user_id = doc.data["user_id"].toString()
                    val uid = doc.id

//                  Then Get User Data
                    val docRef = db.collection("users").document(user_id);
                    docRef.get().addOnSuccessListener { doc ->
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