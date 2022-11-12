package edu.bluejack22_1.beestack.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentTeamBinding
import edu.bluejack22_1.beestack.databinding.FragmentTeamDetailBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.view.Home

class TeamDetailFragment() : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val db = Firebase.firestore;
    lateinit var binding : FragmentTeamDetailBinding;

    lateinit var teamId: String;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamDetailBinding.inflate(inflater, container, false);

        // Query Team Detail


        arguments?.getString("teamId")?.let {
            teamId = it
        }

        db.collection("teams").document(teamId)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val team_motto = value.data?.get("motto")?.toString();
                    val team_name = value.data?.get("name")?.toString();
                    val team_description = value.data?.get("description")?.toString();

                    binding.apply {
                        tvName.text = team_name
                        tvDesc.text = team_description
                        tvMotto.text = team_motto
                        inviteBtn.setOnClickListener {
                            (activity as HomeActivity).replaceFragment(InviteTeamFragment())
                        }
                        leaveBtn.setOnClickListener{
                            CurrentUser.teamId = "";
                            CurrentUser.update().addOnSuccessListener {
                                Home.navigate(context);
                            }
                        }
                    }
                };
            }

        return binding.root;
    }
}