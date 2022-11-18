package edu.bluejack22_1.beestack.activities

import AnswerAdapter
import android.app.ActionBar.LayoutParams
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.ActivityThreadDetailBinding
import edu.bluejack22_1.beestack.model.*
import edu.bluejack22_1.beestack.view.Home


class ThreadDetailActivity : AppCompatActivity() {

    private  lateinit var binding :ActivityThreadDetailBinding

    private var currentThread:Thread? = null;
    private var answerList: MutableList<Answer> = mutableListOf()

    private lateinit var answerAdapter:AnswerAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreadDetailBinding.inflate(layoutInflater)
        val passingThread : Thread= intent.getSerializableExtra("thread") as Thread
        currentThread = passingThread;

        if(!isOwner(passingThread)){
            binding.ownerLayout.visibility = View.INVISIBLE;
        }else{
            setOwnerListener(passingThread);
        }

//        Search Answer Thread
        searchThread(passingThread)
        setAnswerButtonListener(passingThread);
        setUpVoteDownVoteListener(passingThread);

//        Set Data
        Firebase.firestore.collection("threads").document(passingThread.uid).addSnapshotListener {
            doc, e->

            if(e != null){
                return@addSnapshotListener
            }
            if(doc != null)
            {
                val createdAt = doc.getString("created_at");
                val downCount = doc.getString("down_count");
                val description = doc.getString("description");
                val title = doc.getString("title");
                val topCount = doc.getString("top_count");
                val thread = Thread(uid = doc.id, createdAt = createdAt!!, downCount =  downCount!!.toInt(), desc = description!!, title = title!!, topCount = topCount!!.toInt());
               setData(thread)
            }
        }
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
        if(currentThread != null)
        {
            currentThread!!.view += 1;
            currentThread!!.update()
        }

    }

    private fun setOwnerListener(thread: Thread){
        binding.updateBtn.setOnClickListener {
            val dialog : AlertDialog.Builder = AlertDialog.Builder(this);

            val parent = LinearLayout(this)

            parent.layoutParams =
                LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            parent.setPadding(size = 30)

            parent.orientation = LinearLayout.VERTICAL

            val titleET : EditText = EditText(this);
            val descET : EditText = EditText(this);

            titleET.hint = "New Title"
            descET.hint = "New Description"

            titleET.layoutParams =   LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            descET.layoutParams =   LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            parent.addView(titleET);
            parent.addView(descET);

            dialog.setView(parent);

            dialog.setPositiveButton(android.R.string.yes) { dialog, which ->
                thread.title = titleET.text.toString();
                thread.description = descET.text.toString();
                thread.update();
            }

            dialog.show();

        }
        binding.deleteBtn.setOnClickListener{
            Firebase.firestore.collection("threads")
                .document(thread.uid)
                .delete().addOnSuccessListener {
                    Toast.makeText(this, "Succesfully delete thread!",
                        Toast.LENGTH_SHORT).show()
                    finish();
                    Home.navigate(this);
                }
        }
    }

    private fun isOwner(thread : Thread) : Boolean {
        return CurrentUser.uid.equals(thread.user!!.uid);
    }

    private fun setUpVoteDownVoteListener(thread : Thread){
        binding.bottomCountIV.setOnClickListener {
            thread.downCount += 1;
            thread.update();
        }

        binding.topCountIV.setOnClickListener {
            thread.topCount += 1;
            thread.update();
        }
    }

    private fun sendNotification(thread: Thread){
        if(thread.user != null){
            Notification.addNotification(Notification(
                from = CurrentUser.getUser(),
                to = thread.user!!,
                type = "answer-thread",
                data = DataNotification(
                    CurrentUser.username.toString() + " has answered your "+  thread.title+" thread!",
                    thread.uid)
            ))
        }
    }

    private fun searchThread(localThread: Thread){
        val db = Firebase.firestore
        val docRef = db.collection("threads").

//       Listen to answer thread
        document(localThread.uid).collection("answers")
        docRef.addSnapshotListener{ value, e->

            answerList.clear();

            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            for (doc in value!!) {
//                Get Every doc available
                val value = doc.data["answer"].toString()
                val userId = doc.data["user_id"].toString()
                val topCount = doc.data["top_count"].toString();
                val downCount = doc.data["down_count"].toString();
                val answerId = doc.id;

                val temps = doc.data["comments"] as MutableList<HashMap<String, String>>;
                val comments = mutableListOf<Comment>()
                temps.forEach {
                    it->
                    comments.add(Comment(it.get("name").toString(), it.get("value").toString()))

                }


                val userRef = db.collection("users").document(userId)
                userRef.get()
                    .addOnSuccessListener {  doc->
                        if(doc != null){
                            val email:String = doc.data!!.get("email").toString();
                            val location:String = doc.data!!.get("location").toString();
                            val username = doc.data!!.get("username").toString();
                            val url = doc.data!!.get("photo_profile_url").toString()
                            val user = User(doc.id, username, email, location, url);
                            val answer = Answer(user, value, uid= answerId, localThread,
                                topCount = topCount.toInt(), downCount = downCount.toInt(),comments= comments)
                            answerList.add(answer);
                            applyAdapter()
                        }
                    }
            }

        }
    }

    private fun applyAdapter(){
        answerAdapter = AnswerAdapter(answerList)
        binding.apply {
            answerRV.apply{
                layoutManager = LinearLayoutManager(context)
                adapter = answerAdapter
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
            .add(Answer(owner = CurrentUser.getUser(), value = answer).getHashMap())
            .addOnSuccessListener {
                binding.answerET.setText("");
                Toast.makeText(this, "Succesfully Answer", Toast.LENGTH_SHORT).show()
                sendNotification(thread);
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
        binding.downVoteTV.text= thread.downCount.toString();
        binding.upVoteTV.text = thread.topCount.toString();

        binding.description.text = thread.description;
        binding.title.text = thread.title;
        binding.createdAt.text =  thread.createdAt.toString();
    }

}