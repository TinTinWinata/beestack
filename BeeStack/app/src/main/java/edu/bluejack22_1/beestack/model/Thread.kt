package edu.bluejack22_1.beestack.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import edu.bluejack22_1.beestack.`interface`.GetHashMap
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.io.Serializable


class Thread(user:User? = null, desc:String = "", title:String = "", uid:String = "", user_id:String = "", tag: Tag? = null, topCount: Int = 0, downCount:Int = 0, createdAt: String = ""): Serializable, GetHashMap{

    var title:String = title;
    var createdAt:String = createdAt;
    var uid:String= uid;
    var user:User? =user;
    var description:String = desc;
    var user_id: String = user_id;
    var tag:Tag?= tag;
    var topCount: Int = topCount;
    var downCount: Int = downCount;


    public fun getAnswerCollection(): Task<QuerySnapshot> {
        val db = Firebase.firestore;
        return db.collection("threads/${this.uid}/answers").get();
    }

    override fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "title" to this.title,
            "description" to this.description,
            "user_id" to this.user_id,
            "uid" to this.uid,
            "tag_id" to if(this.tag != null) this.tag!!.uid!! else "",
            "top_count" to this.topCount.toString(),
            "down_count" to this.downCount.toString(),
            "created_at" to this.createdAt.toString(),
        )
    }


    public fun getNewHashMap() : HashMap<String, String>{

//        Get object map
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return hashMapOf(
            "title" to this.title,
            "description" to this.description,
            "user_id" to this.user_id,
            "uid" to this.uid,
            "tag_id" to if(this.tag != null) this.tag!!.uid!! else "",
            "top_count" to this.topCount.toString(),
            "down_count" to this.downCount.toString(),
            "created_at" to LocalDateTime.now().format(formatter)
        )
    }

    private fun getRef(): DocumentReference {
        return Firebase.firestore.collection("threads").document(this.uid);
    }


    public fun update() : Task<Void> {
        return this.getRef().set(this.getHashMap());
    }





    private fun photoRefString():String {
        return "threads/${this.uid}"
    }

    private fun photoRef() : StorageReference {
        return FirebaseStorage.getInstance().reference.child(photoRefString())
    }
}