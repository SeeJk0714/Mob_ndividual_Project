package com.example.mobindividualproject.ui.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobindividualproject.R
import com.example.mobindividualproject.data.model.Task
import com.example.mobindividualproject.databinding.AlertSortViewBinding
import com.example.mobindividualproject.databinding.FragmentCompletedBinding
import com.example.mobindividualproject.ui.adapter.WordAdapter
import com.example.mobindividualproject.ui.container.ContainerFragmentDirections
import com.example.mobindividualproject.ui.container.ContainerViewModel
import com.example.mobindividualproject.ui.home.HomeViewModel
import kotlinx.coroutines.launch


class CompletedFragment : Fragment() {
    private lateinit var binding: FragmentCompletedBinding
    private lateinit var adapter: WordAdapter
    private val parentViewModel: ContainerViewModel by viewModels(
        ownerProducer = {requireParentFragment()}
    )
    private val viewModel: CompletedViewModel by viewModels {
        CompletedViewModel.Factory
    }
    private lateinit var searchList: List<Task>
    private var selectedSortOrder = "ascending"
    private var selectedSortBy = "title"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompletedBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.run {
            getAllCompletedTasks()
            tasks.observe(viewLifecycleOwner) {
                searchList = it
                adapter.setTask(it)
                binding.tvNoWord.isInvisible = adapter.itemCount != 0
            }
        }

        lifecycleScope.launch {
            parentViewModel.finish.collect{
                viewModel.getAllCompletedTasks()
            }
        }

        binding.run {
            // this is search function
            svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return true
                }
            })

            //this is sort function
            ivSort.setOnClickListener {
                val alertView = AlertSortViewBinding.inflate(layoutInflater)
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setView(alertView.root)
                val dialog = alertDialog.create()

                alertView.run {
                    rbAscending.setOnClickListener {
                        rbDescending.isChecked = false
                        selectedSortOrder = "ascending"
                    }
                    rbDescending.setOnClickListener {
                        rbAscending.isChecked = false
                        selectedSortOrder = "descending"
                    }
                    rbTitle.setOnClickListener {
                        rbDate.isChecked = false
                        selectedSortBy = "title"
                    }
                    rbDate.setOnClickListener {
                        rbTitle.isChecked = false
                        selectedSortBy = "date"
                    }

                    mbDone.setOnClickListener {
                        val sortList = searchList.sortedWith(compareBy<Task> {
                            if (selectedSortBy == "title") it.title else it.date
                        }.run {
                            if (selectedSortOrder == "ascending") this else reversed()
                        })

                        adapter.setTask(sortList)
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }
    }

    private fun setupAdapter() {
        adapter = WordAdapter(emptyList())
        adapter.listener = object : WordAdapter.Listener {
            override fun onClick(task: Task) {
                findNavController().navigate(
                    ContainerFragmentDirections.actionContainerToShow(task.id!!)
                )
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.run {
            rvCompleted.adapter = adapter
            rvCompleted.layoutManager = layoutManager
        }
    }

    private fun filterList(query: String?) {
        if (query.isNullOrBlank()) {
            adapter.setTask(searchList)
        } else {
            val filter = searchList.filter {
                it.title.contains(query, ignoreCase = true)
            }
            adapter.setTask(filter)
        }
    }

}