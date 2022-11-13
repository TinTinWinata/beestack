import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_1.beestack.activities.ThreadAnswerDetailActivity
import edu.bluejack22_1.beestack.activities.ThreadDetailActivity
import edu.bluejack22_1.beestack.databinding.ActivityThreadAnswerDetailBinding
import edu.bluejack22_1.beestack.databinding.CommentLayoutBinding
import edu.bluejack22_1.beestack.databinding.ThreadAnswerBinding
import edu.bluejack22_1.beestack.model.Answer
import edu.bluejack22_1.beestack.model.Comment

class CommentAdapter (val items : MutableList<Comment>)
    : RecyclerView.Adapter<CommentAdapter.ViewHolder>(){
    private lateinit var binding: CommentLayoutBinding;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CommentLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }



    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView : CommentLayoutBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : Comment){
                binding.commentValue.text = item.value;
                binding.commentOwnerName.text = "by "+item.name;
        }
    }
}