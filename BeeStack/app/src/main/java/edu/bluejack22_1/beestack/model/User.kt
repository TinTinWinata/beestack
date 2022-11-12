package edu.bluejack22_1.beestack.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.Serializable

class User(var uid:String, var username: String, var email: String, var location:String, var photoProfile: String): Serializable {

    companion object{
        fun fromHashMapNoPhoto(hashMap: HashMap<String, String>): User {
            return User(hashMap["uid"]!!, hashMap["username"]!!, hashMap["email"]!!, hashMap["location"]!!, "");
        }
    }






}