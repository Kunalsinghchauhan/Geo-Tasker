package com.ksc.geotasker.ui.fragment

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.ksc.geotasker.R
import com.ksc.geotasker.adapter.TodoAdapter
import com.ksc.geotasker.database.TodoDao
import com.ksc.geotasker.databinding.FragmentHomeBinding
import com.ksc.geotasker.model.TodoViewModel
import com.ksc.geotasker.receiver.TaskBroadcastReceiver
import com.ksc.geotasker.utils.LocalNotification


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = TodoAdapter()
        binding.recyclerView.adapter = adapter
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        todoViewModel.allTasks.observe(viewLifecycleOwner) { task ->
            adapter.todoList = task
            adapter.setData(task)
        }
        binding.fabAddTask.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addOrUpdateTaskFragment)
        }
        return binding.root
    }

}