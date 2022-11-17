package edu.bluejack22_1.beestack.activities

import ThreadAdapter
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.ActivityMyThreadBinding
import edu.bluejack22_1.beestack.databinding.ActivityProfileBinding
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class MyThreadActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMyThreadBinding;
    private var threadList : MutableList<Thread> = mutableListOf()
    private lateinit var threadAdapter: ThreadAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyThreadBinding.inflate(layoutInflater);
        super.onCreate(savedInstanceState)

        setListener();
        fetchMyThread();
        setContentView(binding.root)
    }

    private fun checkThread(){
        Log.d("test", "Checking Thread ... " );
        //        If there's no thread available set no threads layout
        if(threadList.size == 0){
            binding.noThreadLayout.visibility = View.VISIBLE;
        }else{
            binding.noThreadLayout.visibility = View.INVISIBLE;
        }
    }

    private fun applyAdapter(){
        threadAdapter = ThreadAdapter(threadList)
        binding.apply {
            threadRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = threadAdapter
            }
        }
    }

    private fun setListener(){
        binding.backBtn.setOnClickListener {
            onBackPressed();
        }
    }


    private fun fetchMyThread(){
        val db = Firebase.firestore;
        db.collection("threads")
            .whereEqualTo("user_id", CurrentUser.uid)
            .get()
            .addOnSuccessListener { value ->
                for (doc in value!!) {
//                  Get Thread Data
                    val createdAt = doc.data["created_at"].toString();
                    val title = doc.data["title"].toString()
                    val description = doc.data["description"].toString()
                    val user_id = doc.data["user_id"].toString()
                    val uid = doc.id
                    threadList.add(Thread(uid =uid, title = title, desc = description, user_id = user_id, user = CurrentUser.getUser(), createdAt = createdAt));
                    applyAdapter();
                }

                checkThread();
            }
    }

}