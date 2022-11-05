package edu.bluejack22_1.beestack.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.ForgotPasswordActivity
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var firebaseAuth:FirebaseAuth;

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleClient:GoogleSignInClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance();
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(
            R.string.default_web_client_id))
            .requestEmail()
            .build();
        googleClient =  GoogleSignIn.getClient(requireActivity(), options);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        Binding Element (so we don't need to R.findViewById)
        _binding = FragmentLoginBinding.inflate(inflater, container, false);
//        -------------------------


//      Login With Email
        binding.loginBtn.setOnClickListener{

            val email = binding.email.text.toString();
            val password = binding.password.text.toString();

//          Validating Email, Password, Confirm Password
            if(email == "" || password == "") {
                Toast.makeText(context, "Please input all fields", Toast.LENGTH_LONG).show();
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context, "Succesfully Login!", Toast.LENGTH_LONG).show();
//                                             v Call Parent Activity (because we call it in fragment)
                        val intent = Intent(activity, HomeActivity::class.java);
                        startActivity(intent);

                    }else{
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

//        Login With Google
        binding.googleBtn.setOnClickListener{
            googleSignIn();
        }

        binding.forgotPasswordTV.setOnClickListener{
            val i = Intent(context, ForgotPasswordActivity::class.java);
            startActivity(i);
        }

        // Inflate the layout for this fragment
        return binding.root;
    }


//    --------------- Google Sign In code -------------------

    private fun googleSignIn(){
        val googleIntent = googleClient.signInIntent;
        launcher.launch(googleIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
            if(result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result;
            if(account != null){
                updateUi(account);
            }
        }else{
            Toast.makeText(context, task.exception.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private fun updateUi(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                val homeIntent = Intent(context, HomeActivity::class.java);
                startActivity(homeIntent);
            }else{
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
//    --------------------------------------------------------


    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}