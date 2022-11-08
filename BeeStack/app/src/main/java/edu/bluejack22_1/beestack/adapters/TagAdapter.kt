import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.bluejack22_1.beestack.activities.ThreadDetailActivity
import edu.bluejack22_1.beestack.databinding.TagCardBinding
import edu.bluejack22_1.beestack.databinding.ThreadAnswerBinding
import edu.bluejack22_1.beestack.model.Answer
import edu.bluejack22_1.beestack.model.Tag
import edu.bluejack22_1.beestack.model.Thread
import edu.bluejack22_1.beestack.model.User

class TagAdapter (val items : MutableList<Tag>)
    : RecyclerView.Adapter<TagAdapter.ViewHolder>(){
    private lateinit var binding: TagCardBinding;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        binding = TagCardBinding.inflate(inflater,parent,false)
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView : TagCardBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : Tag){
            binding.tagName.text = item.name;
        }
    }


}