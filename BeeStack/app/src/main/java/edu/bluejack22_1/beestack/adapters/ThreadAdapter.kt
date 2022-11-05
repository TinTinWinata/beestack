import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_1.beestack.databinding.ThreadItemBinding
import edu.bluejack22_1.beestack.model.Thread

class ThreadAdapter (val items : MutableList<Thread>)
    : RecyclerView.Adapter<ThreadAdapter.ViewHolder>(){
    private lateinit var binding: ThreadItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= ThreadItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThreadAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView : ThreadItemBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : Thread){
            binding.apply {
                title.text= item.title
                description.text= item.description
            }
        }
    }
}