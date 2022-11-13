import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_1.beestack.databinding.NotificationItemBinding
import edu.bluejack22_1.beestack.model.Notification
import edu.bluejack22_1.beestack.view.TeamInviteDialog

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
                tvTitle.text = item.from.username + " has sent you a invitation"
                root.setOnClickListener {

                    TeamInviteDialog(item.data.teamId).show(
                        act.supportFragmentManager, "TeamInviteDialog")
                }
            }
        }
    }
}