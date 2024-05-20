package com.example.mobindividualproject.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobindividualproject.R
import com.example.mobindividualproject.databinding.FragmentAddBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val viewModel: AddViewModel by viewModels {
        AddViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.finish.collect{
                findNavController().popBackStack()
            }
        }
    }

}