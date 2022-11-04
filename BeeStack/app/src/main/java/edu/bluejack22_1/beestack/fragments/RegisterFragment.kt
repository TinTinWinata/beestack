package edu.bluejack22_1.beestack.fragments

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
import edu.bluejack22_1.beestack.databinding.FragmentRegisterBinding


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



            val email = binding.email.toString();
            val password = binding.password.toString();
            val confirmPassword = binding.confirmPassword.toString();

            Log.d("Credentials", password.toString() + " "  + email.toString())

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful){
                    Log.d("Firebase", "winner!");
                    Toast.makeText(context, "Succesfully register!", Toast.LENGTH_LONG);
                }else{
                    Log.d("Firebase", "failed!" + email + password);
                    Toast.makeText(context, "Didn't succesfully register!", Toast.LENGTH_LONG)
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