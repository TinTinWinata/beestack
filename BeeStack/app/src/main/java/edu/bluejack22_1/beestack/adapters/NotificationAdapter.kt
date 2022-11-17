import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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

                if(item.from.photoProfile != "null"){
                    Picasso.get().load(item.from.photoProfile!!).into(photo);
                }

                tvTitle.text = item.data.message

                if(item.type.equals("answer-thread")){
                    root.setOnClickListener{

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