package edu.bluejack22_1.beestack.view

import android.content.Context
import android.content.Intent
import edu.bluejack22_1.beestack.`interface`.Navigate
import edu.bluejack22_1.beestack.activities.ChangePasswordActivity
import edu.bluejack22_1.beestack.activities.HomeActivity

object ChangePassword:Navigate {
    override fun navigate(context: Context?) {
        if(context != null){
            val i = Intent(context, ChangePasswordActivity::class.java);
            context.startActivity(i);
        }
    }
}