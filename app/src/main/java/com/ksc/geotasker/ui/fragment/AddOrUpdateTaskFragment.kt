package com.ksc.geotasker.ui.fragment


import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ksc.geotasker.R
import com.ksc.geotasker.database.TodoDatabase.Companion.getDatabase
import com.ksc.geotasker.databinding.FragmentAddOrUpdateBinding
import com.ksc.geotasker.model.Todo
import com.ksc.geotasker.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddOrUpdateTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddOrUpdateBinding
    private lateinit var navController: NavController
    private var task: Todo? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).supportActionBar?.hide()
        getDataFromBottom()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddOrUpdateBinding.inflate(inflater, container, false)
        val database = getDatabase(requireContext())
        binding.btnSetLocation.setOnClickListener {
            navController.navigate(R.id.action_addOrUpdateTaskFragment_to_mapFragment)
        }

        binding.iBtnBack.setOnClickListener {
            navController.navigate(R.id.action_addOrUpdateTaskFragment_to_homeFragment)
            navigateToHomeActivity()
        }
        binding.iBtnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (task != null) {
                    // Check if task exists (meaning it's an update operation)
                    CoroutineScope(Dispatchers.IO).launch {
                        Toast.makeText(requireContext(), "Task Updated", Toast.LENGTH_SHORT).show()
                        // Update the existing task in the database
                        database.todoDao()
                            .update(task!!.copy(title = title, description = description))
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        Toast.makeText(requireContext(), "Task Created", Toast.LENGTH_SHORT).show()
                        database.todoDao()
                            .insert(Todo(0, title, description, "8038383", "Lucknow", false))
                    }
                }

            } else {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            }
            navController.navigate(R.id.action_addOrUpdateTaskFragment_to_homeFragment)
            navigateToHomeActivity()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    companion object {}

    private fun navigateToHomeActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
    }

    fun getDataFromBottom() {
        task = arguments?.getSerializable("update_item") as? Todo
        binding.etTitle.setText(task?.title)
        binding.etDescription.setText(task?.description)
    }
}