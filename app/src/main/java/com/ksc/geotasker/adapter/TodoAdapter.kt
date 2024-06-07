package com.ksc.geotasker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.ksc.geotasker.R
import com.ksc.geotasker.model.Todo

class TodoAdapter(val todoList: List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvDateTime: TextView = itemView.findViewById(R.id.tv_date_time)
//        val geoFenceLocation: AppCompatImageButton = itemView.findViewById(R.id.btn_location)
        val cbCompleted: MaterialCheckBox = itemView.findViewById(R.id.cb_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.tvTitle.text = currentItem.title
        holder.tvDescription.text = currentItem.description
//        holder.tvDateTime.text = currentItem.dateAndTime
//        holder.geoFenceLocation.text = currentItem.location
        holder.cbCompleted.isChecked = (currentItem.completed)!!
    }

    override fun getItemCount(): Int = todoList.size
}