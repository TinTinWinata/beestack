package edu.bluejack22_1.beestack.activities

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityThreadDetailBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Thread

class ThreadDetailActivity : AppCompatActivity() {

    private  lateinit var binding :ActivityThreadDetailBinding

    private var thread:Thread? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThreadDetailBinding.inflate(layoutInflater)
        val passingThread : Thread= intent.getSerializableExtra("thread") as Thread

        searchThread(passingThread)
//        setData(thread)
//        setAnswerButtonListener(thread);
        setContentView(binding.root)
    }

    private fun searchThread(localThread: Thread){
        val db = Firebase.firestore
        val docRef = db.collection("threads").
        document(localThread.uid).collection("answers")
        docRef.addSnapshotListener{ value, e->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            for (doc in value!!) {
//                var title="";
//                var description = "";
//                doc.getString("title")?.let {
//                    title = it
//                }
//                doc.getString("description")?.let {
//                    description = it
//                }
//                val thread:Thread = Thread(title,description)
//                thread.uid = doc.id;
            }

        }
    }


    private fun setAnswerButtonListener(thread:Thread){
        binding.answerBtn.setOnClickListener {
            val answer= binding.answerET.text
            if(answer.isNotEmpty()){
                answerThread(thread, answer.toString());
            }
        }
    }

    private fun answerThread(thread:Thread, answer: String){
        val db = Firebase.firestore
        db.collection("threads")
            .document(thread.uid)
            .collection("answers")
            .add(getHashMap(thread, answer))
            .addOnSuccessListener {
                binding.answerET.setText("");
                Toast.makeText(this, "Succesfully Answer", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

    }

    private fun getHashMap(thread:Thread, answer:String) : HashMap<String, String>{
        return hashMapOf(
            "user_id" to CurrentUser.uid,
            "answer" to answer
        )
    }

    private fun setData(thread:Thread){
        binding.description.text = thread.description;
        binding.title.text = thread.title;
    }

}