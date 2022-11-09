package edu.bluejack22_1.beestack.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import edu.bluejack22_1.beestack.`interface`.GetHashMap
import java.io.File
import java.io.Serializable


class Thread(user:User? = null, desc:String = "", title:String = "", uid:String = "", user_id:String = "", tag: Tag? = null): Serializable, GetHashMap{

    var title:String = title;
    var uid:String= uid;
    var user:User? =user;
    var description:String = desc;
    var user_id: String = user_id;
    var tag:Tag?= tag;


    override fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "title" to this.title,
            "description" to this.description,
            "user_id" to this.user_id,
            "uid" to this.uid,
            "tag_id" to if(this.tag != null) this.tag!!.uid!! else ""
        )
    }

    private fun photoRefString():String {
        return "threads/${this.uid}"
    }

    private fun photoRef() : StorageReference {
        return FirebaseStorage.getInstance().reference.child(photoRefString())
    }
}