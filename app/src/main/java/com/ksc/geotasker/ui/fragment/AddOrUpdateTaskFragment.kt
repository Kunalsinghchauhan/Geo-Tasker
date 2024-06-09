package com.ksc.geotasker.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AddOrUpdateTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddOrUpdateBinding
    private lateinit var navController: NavController
    private var task: Todo? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).supportActionBar?.hide()
        getDataFromBottom()
        if (task != null) {
            binding.btnSetLocation.visibility = View.GONE
        }
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
        getCurrentDateTime()
        binding.etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text in the EditText has changed
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text in the EditText is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s.toString()

                // Add a bullet character at the beginning if the EditText is empty or starts with a line break
                if (newText.isEmpty() || newText.startsWith("\n")) {
                    val newTextWithBullet =
                        "\u2022 $newText" // \u2022 is the Unicode for the bullet character
                    binding.etDescription.setText(newTextWithBullet)
                    binding.etDescription.setSelection(newTextWithBullet.length) // Move cursor to the end
                }

                // Check if the last character is a line break (\n)
                val lastChar = if (newText.isNotEmpty()) newText.last() else ' '
                if (lastChar == '\n') {
                    // Add a bullet character after the line break
                    val newTextWithBullet =
                        "$newText\u2022 " // \u2022 is the Unicode for the bullet character
                    binding.etDescription.setText(newTextWithBullet)
                    binding.etDescription.setSelection(newTextWithBullet.length) // Move cursor to the end
                }
            }
        })



        binding.iBtnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                if (task != null) {
                    binding.btnSetLocation.visibility = View.GONE
                    Toast.makeText(requireContext(), "Task Updated", Toast.LENGTH_SHORT).show()
                    // Check if task exists (meaning it's an update operation)
                    CoroutineScope(Dispatchers.IO).launch {

                        // Update the existing task in the database
                        database.todoDao()
                            .update(task!!.copy(title = title, description = description))
                    }
                } else {
                    Toast.makeText(requireContext(), "Task Created", Toast.LENGTH_SHORT).show()
                    val dateTime = getCurrentDateTime()

                    CoroutineScope(Dispatchers.IO).launch {

                        database.todoDao()
                            .insert(Todo(0, title, description, dateTime, "Lucknow", false))
                    }
                }

                navController.navigate(R.id.action_addOrUpdateTaskFragment_to_homeFragment)
                navigateToHomeActivity()
            } else {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            }
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

    fun getCurrentDateTime(): String {
        val dateTime = LocalDateTime.now()
        val formattedDateTime =
            DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy").format(dateTime)
        return formattedDateTime
    }
}