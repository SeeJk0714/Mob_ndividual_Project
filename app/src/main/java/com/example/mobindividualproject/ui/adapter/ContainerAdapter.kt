package com.example.mobindividualproject.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ContainerAdapter(
    fragment: Fragment,
    private val tabs: List<Fragment>
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabs[position]
    }
}