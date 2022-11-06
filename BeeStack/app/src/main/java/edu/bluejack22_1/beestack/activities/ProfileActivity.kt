package edu.bluejack22_1.beestack.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack22_1.beestack.databinding.ActivityProfileBinding
import edu.bluejack22_1.beestack.model.User
import java.net.URI


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProfileBinding.inflate(layoutInflater)

        setData()
        changePhotoProfileListener()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun changePhotoProfileListener(){
        binding.changePhotoProfile.setOnClickListener{
            selectImage()
        }
    }

    private fun selectImage(){
        resultLauncher.launch(arrayOf("image/*"))
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()){
            uploadImage(it)
    }

    private fun uploadImage(image:Uri){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File ... ");
        progressDialog.setCancelable(false)
        progressDialog.show()

        val ref:String = "images/${User.uid}"
        val storageRef = FirebaseStorage.getInstance().getReference(ref);
        storageRef.putFile(image).addOnSuccessListener {

            binding.image.setImageURI(image)
            User.setBitmap()

            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, "Succesfully Change Photoprofile", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show();
        }
    }


    private fun setData(){
        if(User.photoProfileBitmap != null){
            binding.image.setImageBitmap(User.photoProfileBitmap)
        }
        binding.name.text = User.username;
    }
}