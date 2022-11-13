package edu.bluejack22_1.beestack.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentTeamInviteDialogBinding
import edu.bluejack22_1.beestack.fragments.TeamDetailFragment
import edu.bluejack22_1.beestack.model.CurrentUser

class TeamInviteDialog(val teamId: String) : DialogFragment() {

    var binding: FragmentTeamInviteDialogBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentTeamInviteDialogBinding.inflate(LayoutInflater.from(context))

        var db = Firebase.firestore

        binding!!.apply{
            declineBtn.setOnClickListener {

            }
            acceptBtn.setOnClickListener {
                db.collection("users")
                    .document(CurrentUser.uid).update("team_id",teamId).addOnSuccessListener {
                        (activity as HomeActivity).replaceFragment(TeamDetailFragment().apply {
                            arguments = Bundle().apply {
                                putString("teamId", teamId)
                            }
                        });
                    }
            }
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding!!.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "TeamInviteDialog"
    }

}