package com.ksc.geotasker.adapter

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.ksc.geotasker.R
import com.ksc.geotasker.model.Todo

class TodoAdapter() : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var todoList: List<Todo> = ArrayList()

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvDateTime: TextView = itemView.findViewById(R.id.tv_date_time)

        //        val geoFenceLocation: AppCompatImageButton = itemView.findViewById(R.id.btn_location)
        //        val cbCompleted: MaterialCheckBox = itemView.findViewById(R.id.cb_completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.tvTitle.text = "Title: ${currentItem.title}"
        holder.tvDescription.text = currentItem.description
        holder.tvDateTime.text = currentItem.dateAndTime.toString()
//        holder.geoFenceLocation.text = currentItem.location
//        holder.cbCompleted.isChecked = (currentItem.completed)!!
        holder.itemView.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("clicked_item", currentItem)
            it.findNavController()
                .navigate(R.id.action_homeFragment_to_viewTaskBottomFragment, bundle)
        }
    }

    override fun getItemCount(): Int = todoList.size
    fun setData(it: List<Todo>) {
        this.todoList = it
        notifyDataSetChanged()

    }
}