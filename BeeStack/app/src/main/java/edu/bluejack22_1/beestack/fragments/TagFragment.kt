package edu.bluejack22_1.beestack.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.CreateTagActivity
import edu.bluejack22_1.beestack.activities.ForgotPasswordActivity
import edu.bluejack22_1.beestack.databinding.FragmentLoginBinding
import edu.bluejack22_1.beestack.databinding.FragmentTagBinding
import edu.bluejack22_1.beestack.model.Tag

class TagFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentTagBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagBinding.inflate(inflater, container, false);
        setCreateTagListener()
        return binding.root;
    }

    private fun setCreateTagListener(){
        binding.createTagBtn.setOnClickListener {
            val i = Intent(activity, CreateTagActivity::class.java);
            i.putExtra("from", Tag.TAG_FROM_HOME)
            startActivity(i);
        }
    }
}