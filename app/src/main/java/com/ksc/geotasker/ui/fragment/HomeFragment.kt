package com.ksc.geotasker.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksc.geotasker.R
import com.ksc.geotasker.adapter.TodoAdapter
import com.ksc.geotasker.databinding.FragmentHomeBinding
import com.ksc.geotasker.model.TodoViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        recyclerView = binding.recyclerView
        adapter = TodoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fabAddTask.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addOrUpdateTaskFragment)
        }
        return binding.root

    }

}