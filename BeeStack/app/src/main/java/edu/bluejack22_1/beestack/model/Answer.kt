package edu.bluejack22_1.beestack.model

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.`interface`.GetHashMap
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.io.Serializable

class Answer (owner: User, value:String, uid: String = "", thread: Thread? = null, topCount : Int = 0, downCount: Int = 0, comments: MutableList<Comment> = mutableListOf()) : Serializable{

    val value: String = value;
    val owner:User = owner;
    val uid: String = uid;
    val thread: Thread? = thread;
    var topCount: Int =topCount;
    var downCount: Int = downCount;
    val comments: MutableList<Comment> = comments;

    private fun getRef(): DocumentReference {
        return Firebase.firestore.collection("threads").document(thread!!.uid).collection("/answers").document(this.uid);
    }



    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "user_id" to this.owner.uid,
            "answer" to this.value,
            "top_count" to this.topCount.toString(),
            "down_count" to this.downCount.toString(),
            "comments" to comments!!
        );
    }

    public fun update(): Task<Void> {
       return getRef().set(this.getHashMap())
    }
}