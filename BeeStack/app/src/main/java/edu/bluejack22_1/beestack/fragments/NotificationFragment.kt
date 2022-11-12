package edu.bluejack22_1.beestack.fragments

import NotificationAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.FragmentNotificationBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Notification
import edu.bluejack22_1.beestack.model.User

class NotificationFragment : Fragment() {

    private var notificationList : MutableList<Notification> = mutableListOf()
    private lateinit var binding: FragmentNotificationBinding;
    private lateinit var notifAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(layoutInflater)

        fetchNotification();

        applyAdapter();

        return binding.root
    }

    private fun fetchNotification() {
        val db = Firebase.firestore;


        db.collection("notifications")
            .whereEqualTo("to.uid", CurrentUser.uid)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    for (doc in value) {

                        //  Get Notification Data
                        val from = doc.data["from"]
                        val to = doc.data["to"]
                        val type = doc.data["type"].toString()
                        val message = doc.data["message"].toString()

                        notificationList.add(
                            Notification(
                                User.fromHashMap(from as HashMap<String, String>),
                                type,
                                message,
                                User.fromHashMap(to as HashMap<String, String>)
                            )
                        );

                    }
                }

                applyAdapter();
            }

    }

    private fun applyAdapter(){
        notifAdapter = NotificationAdapter(notificationList)
        binding.apply {
            rvNotification.apply{
                layoutManager = LinearLayoutManager(context)
                adapter = notifAdapter
            }
        }
    }

}