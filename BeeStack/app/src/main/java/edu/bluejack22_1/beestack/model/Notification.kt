package edu.bluejack22_1.beestack.model
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Notification(val from: User, val type: String, val data: DataNotification, val to: User)  {

    var uid : String? = null

    companion object{
        fun addNotification(notif: Notification): Task<DocumentReference> {
            var db: FirebaseFirestore = Firebase.firestore;
            return db.collection("notifications")
                .add(notif.getHashMap())
        }
    }

    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "from" to this.from,
            "to" to this.to,
            "type" to this.type,
            "data" to this.data,
        )
    }

    fun setId(newUid: String){
        this.uid = newUid
    }
}

data class DataNotification(val message: String, val id: String);
