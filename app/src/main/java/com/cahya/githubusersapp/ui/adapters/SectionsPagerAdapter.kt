package com.cahya.githubusersapp.ui.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cahya.githubusersapp.ui.detail.fragments.*

class SectionsPagerAdapter(activity: AppCompatActivity, dataBundle: Bundle): FragmentStateAdapter(activity) {

    private var bundle: Bundle

    init {
        bundle = dataBundle
    }

    override fun createFragment(position: Int): Fragment {
        var menu: Fragment? = null
        when(position) {
            0 -> menu = DescriptionFragment()
            1 -> menu = FollowersFragment(true)
            2 -> menu = FollowersFragment(false)
        }
        menu?.arguments = this.bundle
        return menu as Fragment
    }

    override fun getItemCount() = 3

}