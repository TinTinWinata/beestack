package edu.bluejack22_1.beestack.fragments

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack22_1.beestack.databinding.FragmentInsertBinding
import edu.bluejack22_1.beestack.model.Thread


class InsertFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentInsertBinding? = null
    private val binding get() = _binding!!
    var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInsertBinding.inflate(inflater,container,false);

        Log.d(ContentValues.TAG, "Check ${binding.description}");

        val db = Firebase.firestore;

        binding.createBtn.setOnClickListener {
            val title = binding.title.text.toString();
            val description = binding.description.text.toString();
            val user_id = FirebaseAuth.getInstance().currentUser?.uid.toString();

            Log.d(ContentValues.TAG, "Check $title, $description, $user_id");

            db.collection("threads")
                .add(Thread(title,description,user_id).getHashMap())
                .addOnSuccessListener { doc ->
//                    Log.d(TAG, "DocumentSnapshot written with ID: ${doc.id}")
                    if (imageUri != null){
                        uploadImage(imageUri!!, doc.id);
                    }
                }
        }



        binding.imageIcon.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            imagePickerActivityResult.launch(galleryIntent)


        }

        return binding.root;
    }

    private fun uploadImage(image:Uri, id:String){
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading Image ... ");
        progressDialog.setCancelable(false)
        progressDialog.show()

        val ref:String = "threads/${id}"
        val storageRef = FirebaseStorage.getInstance().getReference(ref);
        storageRef.putFile(image).addOnSuccessListener {
            if(progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(context, "Succesfully Upload Image", Toast.LENGTH_SHORT).show()
        }
    }


    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                imageUri = result.data?.data
                binding.newImage.setImageURI(imageUri);
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}