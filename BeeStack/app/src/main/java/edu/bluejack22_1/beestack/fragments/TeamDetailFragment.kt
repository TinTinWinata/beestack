package edu.bluejack22_1.beestack.fragments

import UserAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentHomeBinding
import edu.bluejack22_1.beestack.databinding.FragmentTeamBinding
import edu.bluejack22_1.beestack.databinding.FragmentTeamDetailBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User
import edu.bluejack22_1.beestack.view.Home

class TeamDetailFragment() : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var userList : MutableList<User> = mutableListOf()
    private lateinit var userAdapter: UserAdapter;
    private var participant : Int = 0 ;

    private val db = Firebase.firestore;
    lateinit var binding : FragmentTeamDetailBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamDetailBinding.inflate(inflater, container, false);

        val teamId = arguments?.getString("teamId")
        if(teamId != null){
            findTeam(teamId);
            fetchUser(teamId);
        }

        return binding.root;
    }

    private fun fetchUser(teamId: String){
        val db = Firebase.firestore;
        participant = 0;
        db.collection("users").whereEqualTo("team_id", teamId).get()
            .addOnSuccessListener {
                documents ->
                    for(doc in documents){
                        participant += 1;
                        val username = doc.getString("username").toString();
                        val location = doc.getString("location").toString();
                        val email = doc.getString("email").toString();
                        userList.add(User(uid = doc.id, username=username, location = location, email = email));
                        applyAdapter()
                    }
            }
    }

    private fun setParticipant(){
        binding.tvParticipants.setText(participant.toString() + " participant");
    }

    private fun applyAdapter(){
        setParticipant();
        userAdapter = UserAdapter(userList)
        binding.apply {
            userRV.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userAdapter
            }
        }
    }

//    Query for team detail
    private fun findTeam(teamId: String){
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
    }
}