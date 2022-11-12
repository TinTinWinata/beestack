import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_1.beestack.activities.ThreadDetailActivity
import edu.bluejack22_1.beestack.databinding.ThreadItemBinding
import edu.bluejack22_1.beestack.databinding.UserCardBinding
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class UserAdapter (val items : MutableList<User>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private lateinit var binding: UserCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= UserCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView : UserCardBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : User){
//            If there's any profile views then uncomment this
//            itemView.setOnClickListener {
//                val i = Intent(itemView.context, UserCardBinding::class.java);
//                i.putExtra("user", item);
//                itemView.context.startActivity(i);
//            }
            binding.apply {
                name.text = item.username
            }
        }
    }
}