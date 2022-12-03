package edu.bluejack22_1.beestack.model

import android.util.Log
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


class Thread(user:User? = null, desc:String = "", title:String = "", uid:String = "", user_id:String = "", tag: Tag? = null, topCount: Int = 0, downCount:Int = 0, createdAt: String = "", answer:Int = 0 , view:Int = 0, photoProfileUrl: String = ""): Serializable{

    var title:String = title;
    var createdAt:String = createdAt;
    var uid:String= uid;
    var user:User? =user;
    var description:String = desc;
    var user_id: String = user_id;
    var tag:Tag?= tag;
    var topCount: Int = topCount;
    var downCount: Int = downCount;
    var answer: Int = answer;
    var view: Int = view;
    var photoProfileUrl: String = photoProfileUrl;

    public fun getAnswerCollection(): Task<QuerySnapshot> {
        val db = Firebase.firestore;
        return db.collection("threads/${this.uid}/answers").get();
    }

     public fun getHashMap() : HashMap<String, Any>{
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
            "view" to this.view,
            "photo_profile_url" to this.photoProfileUrl
        )
    }

    public fun getNewHashMap() : HashMap<String, Any>{
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
            "created_at" to LocalDateTime.now().format(formatter),
            "view" to this.view,
            "photo_profile_url" to this.photoProfileUrl
        )
    }

    private fun getRef(): DocumentReference {
        return Firebase.firestore.collection("threads").document(this.uid);
    }


    public fun update() : Task<Void> {
        return this.getRef().set(this.getHashMap());
    }

    public fun updatePhotoProfile(url: String){
        val map: Map<String, String> = mapOf<String, String>("photo_profile_url" to url);
        this.getRef().update(map)
    }

    public fun incrementView(){
        val view : Int = this.view + 1;
        Log.d("test", "updated to : " + view);
        val map: Map<String, Int> = mapOf<String, Int>("view" to view);
        this.getRef().update(map);
    }




    private fun photoRefString():String {
        return "threads/${this.uid}"
    }

    private fun photoRef() : StorageReference {
        return FirebaseStorage.getInstance().reference.child(photoRefString())
    }
}