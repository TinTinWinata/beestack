package edu.bluejack22_1.beestack.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.FragmentTeamInviteDialogBinding

class TeamInviteDialog : DialogFragment() {

    var binding: FragmentTeamInviteDialogBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentTeamInviteDialogBinding.inflate(LayoutInflater.from(context))

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