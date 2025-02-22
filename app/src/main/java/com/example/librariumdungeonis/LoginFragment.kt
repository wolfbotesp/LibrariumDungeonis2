package com.example.librariumdungeonis.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.librariumdungeonis.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            if (username.isNotEmpty()) {
                // Guardar el nombre en SharedPreferences
                val sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("username", username).apply()

                // Navegar directamente al Catálogo (en vez de al menú)
                // tras guardar el nombre:
                val action = LoginFragmentDirections.actionLoginFragmentToCatalogFragment()
                findNavController().navigate(action)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, introduce tu nombre",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
