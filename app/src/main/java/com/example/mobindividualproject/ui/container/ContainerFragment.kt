package com.example.mobindividualproject.ui.container

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import com.example.mobindividualproject.R
import com.example.mobindividualproject.databinding.FragmentContainerBinding
import com.example.mobindividualproject.ui.adapter.ContainerAdapter
import com.example.mobindividualproject.ui.completed.CompletedFragment
import com.example.mobindividualproject.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContainerBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpTabs.adapter = ContainerAdapter(
            this, listOf(HomeFragment(), CompletedFragment())
        )

        TabLayoutMediator(binding.tlTabs, binding.vpTabs) { tab, position ->
            when (position) {
                0 -> tab.text = "Container"
                else -> tab.text = "Completed"
            }
        }.attach()
    }

}