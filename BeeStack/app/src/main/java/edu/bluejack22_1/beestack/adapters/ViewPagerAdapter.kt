package edu.bluejack22_1.beestack.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.bluejack22_1.beestack.fragments.LoginFragment
import edu.bluejack22_1.beestack.fragments.RegisterFragment

class ViewPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
//     Must return how many item that you have for the pager
        return 2; // login & register.
    }

    override fun createFragment(position: Int): Fragment {
//        When Position x then return some fragment
        return when(position){
            0->{
                LoginFragment()
            }
            1->{
                RegisterFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}