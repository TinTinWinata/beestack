package edu.bluejack22_1.beestack.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentCreateTeamBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Team

class CreateTeamFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentCreateTeamBinding;
    private val db = Firebase.firestore;
    private var uri : Uri ? = null ;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View Binding
        binding = FragmentCreateTeamBinding.inflate(inflater, container, false);

        changePhotoProfileListener();
        createTeamOnClick();

        return binding.root;
    }



    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString();



    private fun changePhotoProfileListener(){
        binding.image.setOnClickListener{
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
//           Set URI Global Variable & Set Image (UI)
            val uri: Uri? = data?.data
            if (uri != null) {
                binding.image.setImageURI(uri);
                this.uri = uri;
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getCustomPhotoRef(): StorageReference{
        val ref = "images/team-${binding.etName.text.toString()}";
        val storageRef = FirebaseStorage.getInstance().getReference(ref);
        return storageRef
    }

    private fun uploadImage() : StorageTask<UploadTask.TaskSnapshot>{
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading File ... ");
        progressDialog.setCancelable(false)
        progressDialog.show()
        val storageRef = getCustomPhotoRef();
        return storageRef.putFile(this.uri!!)
            .addOnSuccessListener {
            if(progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener{
            if(progressDialog.isShowing) progressDialog.dismiss()
        }
    }




    fun createTeamOnClick(){
        binding.createTeamBtn.setOnClickListener {
            uploadImage().addOnSuccessListener {
                snapshot ->
                snapshot.storage.downloadUrl.addOnCompleteListener {
                    it.addOnSuccessListener {
                        val imageUrl = it.toString();
                        val name = binding.etName.text.toString();
                        val description = binding.etDesc.text.toString();
                        val motto = binding.etMotto.text.toString();

                        if(name != "" && description != "" && motto != "" && imageUrl != "")
                        {
                            db.collection("teams")
                                .add(Team(name = name, description = description, motto = motto, photoUrl=imageUrl).getHashMap())
                                .addOnSuccessListener { doc ->
                                    val teamId = doc.id;

                                    db.collection("users")
                                        .document(userId).update("team_id",teamId).addOnSuccessListener {
                                            (activity as HomeActivity).replaceFragment(TeamDetailFragment().apply {
                                                arguments = Bundle().apply {
                                                    putString("teamId", teamId)
                                                }
                                            });
                                        }
                                }
                        }else{
                            Toast.makeText(context, "Please validate all inputs!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
        }
    }


}