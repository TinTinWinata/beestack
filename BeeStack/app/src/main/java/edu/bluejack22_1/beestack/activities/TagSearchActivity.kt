package edu.bluejack22_1.beestack.activities

import TagAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.ActivityTagDetailBinding
import edu.bluejack22_1.beestack.databinding.ActivityTagSearchBinding
import edu.bluejack22_1.beestack.model.Tag

class TagSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTagSearchBinding;

    private var tagList : MutableList<Tag> = mutableListOf()
    private lateinit var tagAdapter: TagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagSearchBinding.inflate(layoutInflater);
        val searchedText =intent.getStringExtra("searched_text").toString();
        searchTag(searchedText);
        setToolbar();
        setContentView(binding.root)
    }

    fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.pageName.setText("Create Tag");
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowTitleEnabled(false);
    }




    private fun searchTag(searched_tag: String){
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