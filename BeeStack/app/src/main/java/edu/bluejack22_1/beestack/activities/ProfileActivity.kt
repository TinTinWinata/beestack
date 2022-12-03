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
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.ActivityProfileBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.view.ChangePassword


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProfileBinding.inflate(layoutInflater)

        setData()
        changePhotoProfileListener()
        logoutListener()
        changePasswordListener();

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun logoutListener(){
        binding.logoutBtn.setOnClickListener{
            CurrentUser.logout()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun changePhotoProfileListener(){
        binding.changePhotoProfile.setOnClickListener{
            selectImage()
        }
    }

    private fun selectImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*";
        startActivityForResult(intent, 100)

//        resultLauncher.launch(arrayOf("image/*")) // Old Function
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            val uri: Uri? = data?.data
            if (uri != null) {
                uploadImage(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

//    Old Way
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()){
            uploadImage(it)
    }

    private fun uploadImage(image:Uri){
        Log.d("test-debug", image.toString())
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File ... ");
        progressDialog.setCancelable(false)
        progressDialog.show()

        val ref:String = "images/${CurrentUser.uid}"
        val storageRef = FirebaseStorage.getInstance().getReference(ref);
        storageRef.putFile(image).addOnSuccessListener {


//          Updating the photo profile for globe
            it.storage.downloadUrl.addOnSuccessListener {
                CurrentUser.photoProfileURL = it.toString();
                CurrentUser.update();
            }

            binding.image.setImageURI(image)

//          Set User bitmap for personal listener
            CurrentUser.setBitmap()

            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, getString(R.string.succesfully_change_photo), Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show();
        }
    }

    private fun changePasswordListener(){
        binding.changePasswordBtn.setOnClickListener {
            ChangePassword.navigate(this);
        }
    }


    private fun setData(){
        if(CurrentUser.photoProfileBitmap != null){
            binding.image.setImageBitmap(CurrentUser.photoProfileBitmap)
        }
        binding.name.text = CurrentUser.username;
        binding.location.text = CurrentUser.location;
        binding.email.text = CurrentUser.email;
        binding.tagName.text = CurrentUser.tagName;
    }
}