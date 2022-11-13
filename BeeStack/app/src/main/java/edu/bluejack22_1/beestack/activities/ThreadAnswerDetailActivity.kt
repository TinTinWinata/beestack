package edu.bluejack22_1.beestack.activities

import CommentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack22_1.beestack.databinding.ActivityThreadAnswerDetailBinding
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding
import edu.bluejack22_1.beestack.model.Answer
import edu.bluejack22_1.beestack.model.Comment
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Thread

class ThreadAnswerDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityThreadAnswerDetailBinding;

    private var commentList : MutableList<Comment> = mutableListOf()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreadAnswerDetailBinding.inflate(layoutInflater);
        setContentView(binding.root);
        val passingAnswer: Answer = intent.getSerializableExtra("answer") as Answer;
        setAnswer(passingAnswer);
        setSendListener(passingAnswer);
        applyAdapter(passingAnswer)
    }
    private fun fetchComment(answer :Answer){
        commentList.clear();

        answer.comments.forEach {
            comment->
                commentList.add(comment)
        }
    }

    private fun setAnswer(answer :Answer){
        binding.threadAnswerLayout.apply{
            downVoteTV.text = answer.downCount.toString();
            upVoteTV.text = answer.topCount.toString();
            description.text = answer.value;
            name.text = answer.owner.username;
        }
    }

    private fun setSendListener(answer :Answer){
        binding.sendBtn.setOnClickListener {
            val comment = binding.commentET.text.toString();
            if(comment.isNotEmpty()){
                answer.comments.add(Comment(name = CurrentUser.username, value= comment))
                answer.update();
                Toast.makeText(this, "Succesfully create comment!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }


    private fun applyAdapter(answer :Answer){
        Log.d("test", answer.comments.size.toString())
        commentAdapter = CommentAdapter(answer.comments)
        binding.apply {
            answerRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = commentAdapter
            }
        }
    }




}