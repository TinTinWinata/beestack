package edu.bluejack22_1.beestack.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class Thread(var title: String, var description: String) {
    var user_id: String = ""
    var uid:String= ""
    var photoProfileBitmap: Bitmap? = null


    constructor(title: String, description: String, user_id: String): this(title, description){
        this.user_id = user_id;
    }

    public fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "title" to this.title,
            "description" to this.description,
            "user_id" to this.user_id,
            "uid" to this.uid,
        )
    }

    private fun photoRefString():String {
        return "threads/${this.uid}"
    }

    private fun photoRef() : StorageReference {
        return FirebaseStorage.getInstance().reference.child(photoRefString())
    }


}