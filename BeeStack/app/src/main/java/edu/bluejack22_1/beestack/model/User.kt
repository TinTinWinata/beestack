package edu.bluejack22_1.beestack.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.Serializable

class User(var uid:String, var username: String, var email: String, var location:String, var photoProfile: String? = null, var tagName: String = ""): Serializable {

    companion object{
        fun fromHashMap(hashMap: HashMap<String, String>): User {
            if(hashMap.containsKey("photoProfile")){
                return User(hashMap["uid"]!!, hashMap["username"]!!, hashMap["email"]!!, hashMap["location"]!!, hashMap["photoProfile"], hashMap["tagName"]!!);
            }
            return User(hashMap["uid"]!!, hashMap["username"]!!, hashMap["email"]!!, hashMap["location"]!!, tagName = hashMap["tagName"]!!);
        }
    }






}