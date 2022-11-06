package edu.bluejack22_1.beestack.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentRegisterBinding
import edu.bluejack22_1.beestack.model.User


class RegisterFragment : Fragment() {

    private lateinit var firebaseAuth:FirebaseAuth;

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        firebaseAuth = FirebaseAuth.getInstance();

//        Binding Element (so we don't need to R.findViewById)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false);
//        -------------------------

        binding.registerBtn.setOnClickListener{

            val email = binding.email.text.toString();
            val password = binding.password.text.toString();
            val confirmPassword = binding.confirmPassword.text.toString();

//          Validating Email, Password, Confirm Password
            if(email == "" || password == "" || confirmPassword == ""){
                Toast.makeText(context, "Please input all fields", Toast.LENGTH_LONG).show();
            }else if(password != confirmPassword) {
                Toast.makeText(context, "Your password and confirm password is not same", Toast.LENGTH_LONG).show();
            }

//          Validated
            else{
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context, "Succesfully register!", Toast.LENGTH_LONG).show();

                        //                        Login to user class
                        User.login(firebaseAuth.currentUser!!.uid)

//                                             v Call Parent Activity (because we call it in fragment)
                        val intent = Intent(activity, HomeActivity::class.java);
                        startActivity(intent);

                    }else{
                        Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root;
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}