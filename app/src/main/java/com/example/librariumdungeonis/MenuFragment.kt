package com.example.librariumdungeonis.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.librariumdungeonis.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recibimos el username que viene como argumento (desde el LoginFragment)
        val username = arguments?.getString("username") ?: "Aventurero"
        binding.welcomeTextView.text = "Bienvenido, $username"

        // Ya NO configuramos una segunda BottomNavigationView ni otro NavController.
        // El único NavHost y la única barra se definen y manejan en MainActivity.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
