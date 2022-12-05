package edu.bluejack22_1.beestack.fragments

import TeamInviteAdapter
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.FragmentInviteTeamBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.User

class InviteTeamFragment : Fragment() {

    private var userList : MutableList<User> = mutableListOf()
    private lateinit var binding: FragmentInviteTeamBinding;
    private lateinit var inviteAdapter: TeamInviteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInviteTeamBinding.inflate(layoutInflater)

        fetchUsers();

        binding.backBtn.setOnClickListener {
            activity?.onBackPressed();
        }



        return binding.root;
    }

    private fun fetchUsers() {
        val db = Firebase.firestore;
        db.collection("users")
            .get().addOnSuccessListener { value ->

                for (doc in value!!) {

//                  Get User Data
                    val email = doc.data["email"].toString()
                    val location = doc.data["location"].toString()
                    val username = doc.data["username"].toString()
                    val url = doc.data["photo_profile_url"].toString();

                    val uid = doc.id
                    val tagName = doc.data["tag_name"].toString();
                    val teamId = doc.data["team_id"].toString();

                    if(teamId != "" || teamId == CurrentUser.teamId)
                        continue


                    userList.add(User(uid=uid, username = username, email = email, location = location, photoProfile = url, tagName = tagName));
                    applyAdapter();
                }
            }
    }

    private fun applyAdapter(){
        inviteAdapter = TeamInviteAdapter(activity, userList)
        binding.apply {
            rvInvite.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = inviteAdapter
            }
        }
    }
}