package com.example.githubuserdetailed.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserdetailed.ui.following.FollowingFragment
import com.example.githubuserdetailed.ui.followers.FollowersFragment

class ViewPagerAdapter(fragment: Fragment, val username: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return FollowersFragment.newInstance(username)
            1 -> return FollowingFragment.newInstance(username)
        }
        return FollowersFragment.newInstance(username)
    }
}