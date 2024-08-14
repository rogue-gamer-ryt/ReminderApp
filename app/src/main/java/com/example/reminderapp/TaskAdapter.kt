package com.example.reminderapp
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val context: Context, private var tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.taskTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)
        val dateTextView: TextView = itemView.findViewById(R.id.taskDueDate)
        val detailsButton: Button = itemView.findViewById(R.id.buttonDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title
        holder.descriptionTextView.text = task.description
        holder.dateTextView.text = task.dueDate.toString()

        holder.detailsButton.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_TITLE", task.title)
                putExtra("EXTRA_DESCRIPTION", task.description)
                putExtra("EXTRA_DATE", task.dueDate)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun setTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
