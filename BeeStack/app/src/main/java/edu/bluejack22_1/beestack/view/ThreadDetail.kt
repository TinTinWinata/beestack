package edu.bluejack22_1.beestack.view

import android.content.Context
import android.content.Intent
import edu.bluejack22_1.beestack.`interface`.Navigate
import edu.bluejack22_1.beestack.activities.HomeActivity
import edu.bluejack22_1.beestack.activities.ThreadDetailActivity

class ThreadDetail {

    companion object{
        fun navigate(context: Context?, thread : edu.bluejack22_1.beestack.model.Thread) {
            if(context != null){
                val i = Intent(context, ThreadDetailActivity::class.java);
                i.putExtra("thread", thread);
                context.startActivity(i);
            }
        }
    }
}