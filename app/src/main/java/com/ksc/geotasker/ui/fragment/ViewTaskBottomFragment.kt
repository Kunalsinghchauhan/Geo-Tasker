package com.ksc.geotasker.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ksc.geotasker.R
import com.ksc.geotasker.database.TodoDatabase.Companion.getDatabase
import com.ksc.geotasker.databinding.FragmentViewTaskBottomBinding
import com.ksc.geotasker.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewTaskBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentViewTaskBottomBinding
    private lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = getDatabase(requireContext())
        navController = findNavController()
        val task = arguments?.getSerializable("clicked_item") as? Todo
        task?.let {
            val title = "title - ${it.title}"
            val description = it.description
            val id = it.id
            val dateAndTime = id.toString()
            val isCompleted = it.completed
            val location = it.location

            binding.tvTitleSheet.text = title
            binding.tvDescriptionSheet.text = description
            binding.tvDateTimeSheet.text = dateAndTime
            binding.ivDeleteTask.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    database.todoDao()
                        .delete(Todo(id, title, description, dateAndTime, location, isCompleted))
                }
                dismiss()
            }
        }



        binding.ivEditTask.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("update_item", task)
            task?.let {
                Log.d("ViewTaskBottomFragment", "Navigating with task: $it")
                navController.navigate(
                    R.id.action_viewTaskBottomFragment_to_addOrUpdateTaskFragment,
                    bundle
                )
            } ?: run {
                Log.d("ViewTaskBottomFragment", "Task is null, cannot navigate")
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewTaskBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

    }
}