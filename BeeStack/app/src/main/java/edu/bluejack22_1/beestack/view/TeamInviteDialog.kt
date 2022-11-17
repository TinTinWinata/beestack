package edu.bluejack22_1.beestack.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.databinding.FragmentTeamInviteDialogBinding
import edu.bluejack22_1.beestack.fragments.TeamDetailFragment
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Notification

class TeamInviteDialog(val item: Notification) : DialogFragment() {

    var binding: FragmentTeamInviteDialogBinding? = null
    var db = Firebase.firestore

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentTeamInviteDialogBinding.inflate(LayoutInflater.from(context))



        binding!!.apply{
            declineBtn.setOnClickListener {
                removeNotification();
            }
            acceptBtn.setOnClickListener {
                updateTeam()
                removeNotification();
            }
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding!!.root)
            .create()
    }

    fun removeNotification(){
        db.collection("notifications")
            .document(item.uid!!)
            .delete()
            .addOnSuccessListener { doc ->
                dismiss()
            }
    }

    fun updateTeam(){
        db.collection("users")
            .document(CurrentUser.uid).update("team_id",item.data.id).addOnSuccessListener {
                (activity as HomeActivity).replaceFragment(TeamDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString("teamId", item.data.id)
                    }
                });
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "TeamInviteDialog"
    }

}