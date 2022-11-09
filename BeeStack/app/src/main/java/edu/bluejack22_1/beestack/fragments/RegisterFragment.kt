package edu.bluejack22_1.beestack.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentRegisterBinding
import edu.bluejack22_1.beestack.model.CurrentUser


class RegisterFragment : Fragment() {

    private lateinit var firebaseAuth:FirebaseAuth;

    private val locations  = arrayOf("Kemanggisan", "Alam Sutera", "Bekasi", "Malang")
    private var selectedLocation:String = "";

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initLocations(){
        val locationAdapter : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.list_locations, locations);
        binding.locationACTV.setAdapter(locationAdapter)
        binding.locationACTV.setOnItemClickListener { adapterView, view, i, l ->
             selectedLocation = adapterView.getItemAtPosition(i).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance();
//        Binding Element (so we don't need to R.findViewById)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false);
//        -------------------------

        initLocations();

        binding.registerBtn.setOnClickListener{
            val email = binding.email.text.toString();
            val password = binding.password.text.toString();
            val confirmPassword = binding.confirmPassword.text.toString();
            val username = binding.username.text.toString()

//          Validating Email, Password, Confirm Password
            if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedLocation.isEmpty() || username.isEmpty()){
                Toast.makeText(context, "Please input all fields", Toast.LENGTH_LONG).show();
            }else if(password != confirmPassword) {
                Toast.makeText(context, "Your password and confirm password is not same", Toast.LENGTH_LONG).show();
            }
//          Validated
            else{
                Log.d("test", selectedLocation.toString())

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(context, "Succesfully register!", Toast.LENGTH_LONG).show();
                        CurrentUser.username = username;
                        CurrentUser.location = selectedLocation

                        //                        Login to user class
                        CurrentUser.login(firebaseAuth.currentUser!!.uid)

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