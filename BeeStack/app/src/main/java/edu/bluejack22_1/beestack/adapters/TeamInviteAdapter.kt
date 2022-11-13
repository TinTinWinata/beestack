import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.databinding.TeamInviteItemBinding
import edu.bluejack22_1.beestack.model.CurrentUser
import edu.bluejack22_1.beestack.model.DataInvite
import edu.bluejack22_1.beestack.model.Notification
import edu.bluejack22_1.beestack.model.User


class TeamInviteAdapter(val ctx: FragmentActivity?, val items: MutableList<User>)
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
                    addNotification(Notification(
                        from = User(
                              CurrentUser.uid, CurrentUser.username, CurrentUser.email, CurrentUser.location, CurrentUser.photoProfileURL),
                        to = item,
                        type = "team-invite",
                        data = DataInvite(
                            CurrentUser.username.toString() + " has invited you to a team !",
                            CurrentUser.teamId
                        )
                    ));
                }
            }
        }
    }

    fun addNotification(notif: Notification){
        var db = Firebase.firestore;
        db.collection("notifications")
            .add(notif.getHashMap())
            .addOnSuccessListener { doc ->
                val toast = Toast.makeText(
                    ctx,
                    "User Invited !",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
    }
}