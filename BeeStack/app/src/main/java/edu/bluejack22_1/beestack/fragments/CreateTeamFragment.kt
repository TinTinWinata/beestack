package edu.bluejack22_1.beestack.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentCreateTeamBinding
import edu.bluejack22_1.beestack.model.Team

class CreateTeamFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentCreateTeamBinding;
    private val db = Firebase.firestore;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View Binding
        binding = FragmentCreateTeamBinding.inflate(inflater, container, false);

        createTeamOnClick();

        return binding.root;
    }

    val userId = FirebaseAuth.getInstance().currentUser?.uid.toString();

    fun createTeamOnClick(){
        binding.createTeamBtn.setOnClickListener {
            val name = binding.etName.text.toString();
            val description = binding.etDesc.text.toString();

            if(name != "" && description != "")
                db.collection("teams")
                    .add(Team(name, description).getHashMap())
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
        }
    }


}