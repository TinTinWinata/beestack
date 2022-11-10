package edu.bluejack22_1.beestack.model

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import edu.bluejack22_1.beestack.`interface`.GetHashMap
import java.io.Serializable


class Thread(user:User? = null, desc:String = "", title:String = "", uid:String = "", user_id:String = "", tag: Tag? = null, topCount: Int = 0, downCount:Int = 0): Serializable, GetHashMap{

    var title:String = title;
    var uid:String= uid;
    var user:User? =user;
    var description:String = desc;
    var user_id: String = user_id;
    var tag:Tag?= tag;
    var topCount: Int = topCount;
    var downCount: Int = downCount;


    override fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "title" to this.title,
            "description" to this.description,
            "user_id" to this.user_id,
            "uid" to this.uid,
            "tag_id" to if(this.tag != null) this.tag!!.uid!! else "",
            "top_count" to this.topCount.toString(),
            "down_count" to this.downCount.toString()
        )
    }

    private fun photoRefString():String {
        return "threads/${this.uid}"
    }

    private fun photoRef() : StorageReference {
        return FirebaseStorage.getInstance().reference.child(photoRefString())
    }
}