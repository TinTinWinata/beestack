import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack22_1.beestack.activities.ThreadAnswerDetailActivity
import edu.bluejack22_1.beestack.activities.ThreadDetailActivity
import edu.bluejack22_1.beestack.databinding.ThreadAnswerBinding
import edu.bluejack22_1.beestack.model.Answer

class AnswerAdapter (val items : MutableList<Answer>)
    : RecyclerView.Adapter<AnswerAdapter.ViewHolder>(){
    private lateinit var binding: ThreadAnswerBinding;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ThreadAnswerBinding.inflate(inflater,parent,false)
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }



    override fun getItemCount() = items.size

    //View Holder
    inner class ViewHolder(itemView : ThreadAnswerBinding) : RecyclerView.ViewHolder(itemView.root){
        fun bind(item : Answer){

            itemView.setOnClickListener {
                val i = Intent(itemView.context, ThreadAnswerDetailActivity::class.java);
                i.putExtra("answer", item);
                itemView.context.startActivity(i);
            }

            binding.name.text = item.owner.username;
            binding.description.text = item.value;

            binding.upVoteTV.text = item.topCount.toString();
            binding.downVoteTV.text = item.downCount.toString();

            binding.bottomCountIV.setOnClickListener {
                item.downCount += 1;
                item.update();
            }

            binding.topCountIV.setOnClickListener {
                item.topCount += 1;
                item.update();
            }


        }
    }


}