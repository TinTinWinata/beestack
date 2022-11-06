package edu.bluejack22_1.beestack.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import kotlin.properties.Delegates

object User {

    var uid:String= ""
    var email:String = "";
    var username:String = "";
    var location:String = "";

    var photoProfileListListener = ArrayList<() -> Unit>()

    var photoProfileBitmap:Bitmap? by Delegates.observable(null){
        property, oldValue, newValue ->
        photoProfileListListener.forEach {
            it()
        }
    }

    fun isEmpty() : Boolean{
        return uid.isEmpty()
    }
    private fun photoProfileRefString():String {
        return "images/${this.uid}"
    }

    private fun photoProfileRef() : StorageReference{
        return FirebaseStorage.getInstance().reference.child(photoProfileRefString())
    }

    fun setBitmap(){
        val ref:StorageReference = this.photoProfileRef()
        val localFile = File.createTempFile(uid, "jpg");
        Log.d("user-activity", "setting bitmap...")
        ref.getFile(localFile).addOnSuccessListener {

            this.photoProfileBitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            Log.d("user-activity", this.photoProfileBitmap.toString())
        }
    }

    fun getUserRef(): DocumentReference{
        return Firebase.firestore.collection("users").document(uid);
    }

    fun login(uid : String){
        val db = Firebase.firestore
        val ref = db.collection("users").document(uid)
        ref.addSnapshotListener{ snapshot, e ->
            if(e != null){
                return@addSnapshotListener
            }
            if(snapshot != null && snapshot.exists()){
//              Succesfully snapshot
                this.uid = snapshot.data!!.get("uid").toString()
                this.username = snapshot.data!!.get("username").toString()
                this.email = snapshot.data!!.get("email").toString()
                this.location = snapshot.data!!.get("location").toString()

//              Set bitmap for photo profile always when login
                this.setBitmap()
            }else if(snapshot != null && !snapshot.exists()){
//                Create new default user
                createDocBasedOnFirebase(uid)
            }
        }
    }

    private fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "uid" to this.uid,
            "email" to this.email,
            "username" to this.username,
            "location" to this.location,
        )
    }

    private fun createDocBasedOnFirebase(uid: String){
        val firebaseAuth = FirebaseAuth.getInstance()
        createNewDoc(firebaseAuth.currentUser!!.displayName.toString(), "", uid, firebaseAuth.currentUser!!.email.toString())
    }

    private fun makeUserDoc(){
//      Make user doc can't be empty
        if(isEmpty()){
            return;
        }

        val db = Firebase.firestore
        db.collection("users")
            .document(this.uid).set(getHashMap())
            .addOnSuccessListener {
                login(this.uid)
            }
            .addOnFailureListener{
                Log.d("MyFirestoreDb", it.message.toString())
            }
    }

    fun createNewDoc(username:String, location: String, uid: String, email: String){
//        Assign every variable to this user singleton variable
        this.uid = uid
        this.username = username
        this.email = email
        this.location = location
        makeUserDoc()
    }

    private fun emptyAllAttr(){
        this.uid = "";
        this.email = "";
        this.location = "";
        this.username = "";
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        emptyAllAttr()
    }
}