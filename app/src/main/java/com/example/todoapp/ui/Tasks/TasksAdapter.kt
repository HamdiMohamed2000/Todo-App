package com.example.todoapp.ui.Tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.ItemTaskBinding




class TasksAdapter(private val listener : onItemClickLisener ) : androidx.recyclerview.widget.ListAdapter<Task,TasksAdapter.TasksViewHolder>(Diffcallback()) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
            val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
               return  TasksViewHolder(binding)
      }

   override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
         val currentItem = getItem(position)
      holder.bind(currentItem)
      }
  inner class TasksViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

     init {
         binding.apply {
            root.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION){
                  val task = getItem(position)
                  listener.onItemClick(task)
               }
            }

            checkBoxCompleted.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION){
                  val task = getItem(position)
                  listener.onChekBoxClick(task , checkBoxCompleted.isChecked)
               }
            }
         }
     }
      fun bind(task: Task) {
         binding.apply {
            checkBoxCompleted.isChecked = task.completed
            textViewName.text = task.name
            textViewName.paint.isStrikeThruText = task.completed
            labelPriority.isVisible = task.important
         }
      }
   }

   interface onItemClickLisener{
      fun onItemClick(task: Task)
      fun onChekBoxClick(task: Task , isChecked : Boolean)
   }

   class Diffcallback : DiffUtil.ItemCallback<Task>() {

      override fun areItemsTheSame(oldItem: Task, newItem: Task) =
         oldItem.id == newItem.id


      override fun areContentsTheSame(oldItem: Task, newItem: Task) =
         oldItem == newItem


   }



}




