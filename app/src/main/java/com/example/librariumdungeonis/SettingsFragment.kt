package com.example.librariumdungeonis.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.librariumdungeonis.R
import com.example.librariumdungeonis.databinding.FragmentSettingsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val profileMap = mapOf(
        "perfil1" to R.drawable.perfil1,
        "perfil2" to R.drawable.perfil2,
        "perfil3" to R.drawable.perfil3
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spSettings = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val username = spSettings.getString("username", "Aventurero")

        // "Usuario: X" -> localizamos "Usuario:" con R.string.username_label
        val userLabel = getString(R.string.username_label)
        binding.userNameTextView.text = "$userLabel $username"

        // Leer favoritos guardados
        val spFavorites = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = spFavorites.getString("favorites", null)
        val type = object : TypeToken<List<String>>() {}.type
        val favoriteIds: List<String> = gson.fromJson(json, type) ?: emptyList()

        // "Favoritos: ..." -> localizamos "Favoritos:" con R.string.favorites_label
        val favLabel = getString(R.string.favorites_label)
        if (favoriteIds.isEmpty()) {
            binding.favoritesTextView.text = "$favLabel Sin favoritos"
        } else {
            binding.favoritesTextView.text = "$favLabel ${favoriteIds.joinToString(", ")}"
        }

        // Imagen de perfil
        val currentProfile = spSettings.getString("profileImage", "perfil1")
        updateProfileImage(currentProfile ?: "perfil1")

        when (currentProfile) {
            "perfil1" -> binding.radioPerfil1.isChecked = true
            "perfil2" -> binding.radioPerfil2.isChecked = true
            "perfil3" -> binding.radioPerfil3.isChecked = true
        }

        binding.profileRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadio = group.findViewById<RadioButton>(checkedId)
            val selectedProfileKey = when (selectedRadio) {
                binding.radioPerfil1 -> "perfil1"
                binding.radioPerfil2 -> "perfil2"
                binding.radioPerfil3 -> "perfil3"
                else -> "perfil1"
            }
            spSettings.edit().putString("profileImage", selectedProfileKey).apply()
            updateProfileImage(selectedProfileKey)
        }

        // Modo oscuro
        val isDark = spSettings.getBoolean("darkTheme", false)
        binding.switchTheme.isChecked = isDark
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            spSettings.edit().putBoolean("darkTheme", isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun updateProfileImage(profileKey: String) {
        val resourceId = profileMap[profileKey] ?: R.drawable.perfil1
        binding.profileImageView.setImageResource(resourceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
