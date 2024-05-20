package com.example.mobindividualproject.ui.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobindividualproject.R
import com.example.mobindividualproject.databinding.FragmentUpdateBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val viewModel: UpdateViewModel by viewModels {
        UpdateViewModel.Factory
    }
    private var selectedTaskId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(
            layoutInflater, container, false
        )
        viewModel.task.observe(viewLifecycleOwner) {
            it.let {
                viewModel.setTask(it)
            }
        }
        arguments?.let {
            selectedTaskId = UpdateFragmentArgs.fromBundle(it).id
            viewModel.getTask(selectedTaskId)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.run {
                showSnackbar.observe(viewLifecycleOwner) {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                }
                finish.collect {
                    findNavController().popBackStack()
                }
            }
        }
    }

}