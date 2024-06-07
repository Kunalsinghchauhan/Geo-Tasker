package com.ksc.geotasker.ui.fragment


import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ksc.geotasker.R
import com.ksc.geotasker.databinding.FragmentAddOrUpdateBinding
import com.ksc.geotasker.ui.MainActivity

class AddOrUpdateTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddOrUpdateBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddOrUpdateBinding.inflate(inflater, container, false)
        binding.btnSetLocation.setOnClickListener {
            navController.navigate(R.id.action_addOrUpdateTaskFragment_to_mapFragment)
        }

        binding.iBtnBack.setOnClickListener {
            navController.navigate(R.id.action_addOrUpdateTaskFragment_to_homeFragment)
            navigateToHomeActivity()
        }
        binding.iBtnSubmit.setOnClickListener {
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

    private fun Fragment.navigateToHomeActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
    }
}