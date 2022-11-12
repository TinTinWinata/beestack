package edu.bluejack22_1.beestack.model;

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.`interface`.GetHashMap
import java.io.Serializable

class Tag(uid:String?=null, name:String,description:String) : GetHashMap, Serializable{
    var uid:String? = uid;
    var name :String = name;
    var description :String = description;

//    Static Variable
//    Companion -> create new instance automatic without using keyword new
    companion object{
        val db = Firebase.firestore
        val REF = db.collection("tags");
        val TAG_FROM_DETAIL = 1
        val TAG_FROM_HOME = 0;
    }

    override fun getHashMap(): HashMap<String, String> {
        return hashMapOf(
            "name" to this.name,
            "description" to this.description,
        )
    }


}

