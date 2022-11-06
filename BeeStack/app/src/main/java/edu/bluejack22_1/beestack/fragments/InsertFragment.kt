package edu.bluejack22_1.beestack.fragments

import android.R.attr.data
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import edu.bluejack22_1.beestack.databinding.FragmentInsertBinding


class InsertFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentInsertBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInsertBinding.inflate(inflater,container,false);

        Log.d(ContentValues.TAG, "Check ${binding.description}");

        binding.createBtn.setOnClickListener {
            val title = binding.title?.text.toString();
            val description = binding.description.text.toString();
            val user_id = FirebaseAuth.getInstance().currentUser.toString();

            Log.d(ContentValues.TAG, "Check $title");
        }

        return binding.root;
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}