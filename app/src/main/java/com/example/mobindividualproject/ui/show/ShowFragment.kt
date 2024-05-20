package com.example.mobindividualproject.ui.show

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobindividualproject.R
import com.example.mobindividualproject.databinding.AlertCompletedViewBinding
import com.example.mobindividualproject.databinding.AlertDeleteViewBinding
import com.example.mobindividualproject.databinding.FragmentShowBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShowFragment : Fragment() {
    private lateinit var binding: FragmentShowBinding
    private val viewModel: ShowViewModel by viewModels {
        ShowViewModel.Factory
    }
    private var selectedTaskId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowBinding.inflate(
            layoutInflater, container, false
        )
        arguments?.let {
            selectedTaskId = ShowFragmentArgs.fromBundle(it).id
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
                task.observe(viewLifecycleOwner) {
                    it?.let {
                        binding.run {
                            tvTitleB.text = it.title
                            tvMeaning.text = it.mean
                            tvSynonym.text = it.synonym
                            tvDetail.text = it.detail
                            // If status is true, mbDone will be hidden.
                            if (it.status == true) {
                                mbDone.isInvisible = true
                            }
                        }
                    }
                }
            }
        }

        binding.run {

            mbDone.setOnClickListener {
                val alertView = AlertCompletedViewBinding.inflate(layoutInflater)
                val doneDialog = AlertDialog.Builder(requireContext())
                doneDialog.setView(alertView.root)
                val dialog = doneDialog.create()

                alertView.run {
                    mbYes.setOnClickListener {
                        viewModel!!.completeTask()
                        findNavController().navigate(
                            ShowFragmentDirections.actionShowToContainer()
                        )
                        dialog.dismiss()
                    }

                    mbNo.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.show()
                }
            }

            mbUpdate.setOnClickListener {
                findNavController().navigate(
                    ShowFragmentDirections.actionShowToUpdate(selectedTaskId)
                )
            }

            mbDelete.setOnClickListener {
                val alertView = AlertDeleteViewBinding.inflate(layoutInflater)
                val deleteDialog = AlertDialog.Builder(requireContext())
                deleteDialog.setView(alertView.root)
                val dialog = deleteDialog.create()

                alertView.run {
                    // If you click this button, it will be deleted and the "Deleted Successful" will be displayed.
                    mbDelete.setOnClickListener {
                        viewModel!!.delete()
                        findNavController().navigate(
                            ShowFragmentDirections.actionShowToContainer()
                        )
                        Toast.makeText(
                            requireContext(), "Deleted Successful", Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }

                    // If you click this button, it will be return to the ShowFragment
                    mbCancel.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.show() // Show the delete dialog
                }

            }
        }
    }

}