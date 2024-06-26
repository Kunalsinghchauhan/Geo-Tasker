package com.ksc.geotasker.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.ksc.geotasker.R
import com.ksc.geotasker.database.TodoDatabase
import com.ksc.geotasker.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TodoAdapter() : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

     var todoList: List<Todo> = ArrayList()

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvDateTime: TextView = itemView.findViewById(R.id.tv_date_time)

        //        val geoFenceLocation: AppCompatImageButton = itemView.findViewById(R.id.btn_location)
        val cbCompleted: MaterialCheckBox = itemView.findViewById(R.id.cb_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.tvTitle.text = currentItem.title
        holder.tvDescription.text = currentItem.description
        holder.tvDateTime.text = currentItem.dateAndTime.toString()
//        holder.geoFenceLocation.text = currentItem.location
        holder.cbCompleted.isChecked = (currentItem.completed)!!
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clicked_item", currentItem)
            it.findNavController()
                .navigate(R.id.action_homeFragment_to_viewTaskBottomFragment, bundle)
        }
        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val database = TodoDatabase.getDatabase(holder.itemView.context)
                CoroutineScope(Dispatchers.IO).launch {
                    delay(500)
                    database.todoDao().delete(currentItem)
                }

                Toast.makeText(holder.itemView.context, "Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = todoList.size
    fun setData(it: List<Todo>) {
        this.todoList = it
        notifyDataSetChanged()
    }
}