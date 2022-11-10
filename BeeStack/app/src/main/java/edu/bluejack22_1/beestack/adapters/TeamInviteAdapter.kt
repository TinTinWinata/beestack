import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_1.beestack.databinding.TeamInviteItemBinding
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
            }

//            itemView.setOnClickListener {
//                val i = Intent(itemView.context, ThreadDetailActivity::class.java);
//                i.putExtra("thread", item);
//                itemView.context.startActivity(i);
//            }
//            binding.apply {
//                title.text= item.title
//                description.text= item.description
//                credential.text = item.user?.username
//            }
        }
    }
}