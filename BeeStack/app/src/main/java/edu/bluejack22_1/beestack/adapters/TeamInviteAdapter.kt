import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.TeamInviteItemBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.Notification
import edu.bluejack22_1.beestack.model.User


class TeamInviteAdapter (val items : MutableList<User>)
    : RecyclerView.Adapter<TeamInviteAdapter.ViewHolder>(){
    private lateinit var binding: TeamInviteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamInviteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= TeamInviteItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamInviteAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView: TeamInviteItemBinding) : RecyclerView.ViewHolder(itemView.root){


        fun bind(item: User){

            binding.apply {
                tvUsername.text = item.username
                inviteBtn.setOnClickListener {
                    addNotification(itemView, Notification(
                        from = User(
                              CurrentUser.uid, CurrentUser.username, CurrentUser.email, CurrentUser.location),
                        to = item,
                        type = "team-invite",
                        message = CurrentUser.username.toString() + " has invited you to a team !"
                    ));
                }
            }
        }
    }

    fun addNotification(itemView: View, notif: Notification){
        var db = Firebase.firestore;
        db.collection("notifications")
            .add(notif.getHashMap())
            .addOnSuccessListener { doc ->
                val toast = Toast.makeText(
                    itemView.context,
                    "User Invited !",
                    Toast.LENGTH_SHORT
                )
                toast.setMargin(50f, 50f)
                toast.show()
            }
    }
}