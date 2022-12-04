import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import edu.bluejack22_1.beestack.R
import edu.bluejack22_1.beestack.databinding.NotificationItemBinding
import edu.bluejack22_1.beestack.model.Notification
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User
import edu.bluejack22_1.beestack.view.TeamInviteDialog
import edu.bluejack22_1.beestack.view.ThreadDetail

class NotificationAdapter (val act: FragmentActivity, val items : MutableList<Notification>)
    : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    private lateinit var binding: NotificationItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= NotificationItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView: NotificationItemBinding) : RecyclerView.ViewHolder(itemView.root){


        fun bind(item: Notification){

            binding.apply {

                if(item.from.photoProfile != null && item.from.photoProfile!!.isNotEmpty()){
                    Picasso.get().load(item.from.photoProfile!!).into(photo);
                }

                tvTitle.text = item.data.message

                if(item.type.equals("answer-thread")){
                    root.setOnClickListener{
                        val db : FirebaseFirestore = Firebase.firestore;
                        db.collection("threads").document(item.data.id).get().addOnSuccessListener {
                            doc ->
//                  Get Thread Data
                            val title = doc.getString("title").toString()
                            val description = doc.getString("description").toString()
                            val user_id = doc.getString("user_id").toString()
                            val createdAt = doc.getString("created_at").toString();
                            val topCount = doc.getString("top_count").toString();
                            val downCount = doc.getString("down_count").toString();
                            val view = doc.getLong("view")!!.toInt();
                            val uid = doc.id
//                  Then Get User Data
                            val docRef = db.collection("users").document(user_id);
                            docRef.get()
                                .addOnSuccessListener { userDoc ->
                                    if (userDoc != null) {
                                        val username = userDoc.data!!["username"].toString()
                                        val email = userDoc.data!!["email"].toString()
                                        val location = userDoc.data!!["location"].toString()
                                        val photoProfile = userDoc.data!!["photo_profile_url"].toString();
                                        var tagName = doc.data!!["tag_name"].toString();
                                        val user: User =
                                            User(userDoc.id, username, email, location, photoProfile, tagName= tagName)

//                           Add add getted data to the thread list (Vector)
                                        val thread: Thread = Thread(uid =uid, title = title, desc = description, user_id = user_id, user = user, createdAt = createdAt, topCount = topCount.toInt(), downCount = downCount.toInt(), view = view.toInt());
                                        Notification.delete(item.uid.toString()).addOnSuccessListener {
                                            ThreadDetail.navigate(itemView.context, thread);
//                                            val text: String= R.string.deleted
//                                            Toast.makeText(itemView.context, item.uid.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                        }
                    }
                }else if(item.type.equals("team-invite")){
                    root.setOnClickListener {
                        TeamInviteDialog(item).show(
                            act.supportFragmentManager, "TeamInviteDialog")
                    }
                }
            }
        }
    }

}