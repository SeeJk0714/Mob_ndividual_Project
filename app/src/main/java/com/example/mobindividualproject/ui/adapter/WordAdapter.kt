package com.example.mobindividualproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobindividualproject.data.model.Task
import com.example.mobindividualproject.databinding.LayoutFileItemBinding
import com.example.mobindividualproject.generated.callback.OnClickListener.Listener

class WordAdapter(
    private var tasks: List<Task>
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutFileItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WordItemViewFolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = tasks[position]
        if (holder is WordItemViewFolder) {
            holder.bind(task)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun setTask(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class WordItemViewFolder(
        private val binding: LayoutFileItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.task = task
            binding.cvFile.setOnClickListener {
                listener?.onClick(task)
            }
        }
    }

    interface Listener {
        fun onClick(task: Task)
    }
}