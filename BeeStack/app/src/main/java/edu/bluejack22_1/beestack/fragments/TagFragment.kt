package edu.bluejack22_1.beestack.fragments

import TagAdapter
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.CreateTagActivity
import edu.bluejack22_1.beestack.activities.ForgotPasswordActivity
import edu.bluejack22_1.beestack.activities.TagSearchActivity
import edu.bluejack22_1.beestack.databinding.FragmentLoginBinding
import edu.bluejack22_1.beestack.databinding.FragmentTagBinding
import edu.bluejack22_1.beestack.model.Tag
import edu.bluejack22_1.beestack.model.Thread

class TagFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentTagBinding? = null
    private val binding get() = _binding!!
    private var tagList : MutableList<Tag> = mutableListOf()
    private lateinit var tagAdapter: TagAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagBinding.inflate(inflater, container, false);
        setSearchListener();
        setCreateTagListener()
        fetchTag()
        return binding.root;
    }

    private fun setCreateTagListener(){
        binding.createTagBtn.setOnClickListener {
            val i = Intent(activity, CreateTagActivity::class.java);
            i.putExtra("from", Tag.TAG_FROM_HOME)
            startActivity(i);
        }
    }

    private fun fetchTagWithName(searched_tag: String){
        tagList.clear();
        if(searched_tag.isEmpty()){
            fetchTag();
            return;
        }
        val db = Firebase.firestore;
        db.collection("tags")
            .orderBy("name")
            .startAt(searched_tag)
            .endAt(searched_tag+"\uf8ff")
            .get()
            .addOnSuccessListener { documents->
                for(doc in documents){
                    Log.d("test", doc.id);
                    val name = doc.data["name"].toString()
                    val description = doc.data["description"].toString()
                    tagList.add(Tag(doc.id, name, description))
                    applyAdapter();
                }
            }
            .addOnFailureListener{

            }
    }

    private fun setSearchListener(){
        binding.searchBtn.setOnClickListener {
            if(binding.searchET!!.text!!.isNotEmpty()){
                val searchedText = binding.searchET.text.toString();

//                Create new intent when search
//                val i = Intent(context, TagSearchActivity::class.java);
//                i.putExtra("searched_text", searchedText);
//                startActivity(i);

//                Or just fetch in this intent ?
                fetchTagWithName(searchedText);
            }else{
                fetchTag();
            }
        }
    }

    private fun fetchTag(){
        tagList.clear();
        val db = Firebase.firestore;
        db.collection("tags")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
//                  Get Thread Data
                    val name = doc.data["name"].toString()
                    val description = doc.data["description"].toString()
                    tagList.add(Tag(doc.id, name, description))
                }
                applyAdapter()
            }
    }


    private fun applyAdapter(){
        tagAdapter = TagAdapter(tagList)
        binding.apply {
            tagRV.apply {
                layoutManager = GridLayoutManager(context, 2);
                adapter = tagAdapter
                setHasFixedSize(true)
            }
        }
    }
}