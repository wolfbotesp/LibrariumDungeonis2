package com.example.librariumdungeonis.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.librariumdungeonis.R
import com.example.librariumdungeonis.databinding.FragmentFavoritesBinding
import com.example.librariumdungeonis.ui.catalog.adapter.CatalogAdapter
import com.example.librariumdungeonis.ui.catalog.model.CatalogItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CatalogAdapter
    private var favoriteItems = mutableListOf<CatalogItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteItems = loadFavorites()

        if (favoriteItems.isEmpty()) {
            binding.emptyFavoritesTextView.visibility = View.VISIBLE
        } else {
            binding.emptyFavoritesTextView.visibility = View.GONE
        }

        adapter = CatalogAdapter(requireContext(), favoriteItems)
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.adapter = adapter
    }

    private fun loadFavorites(): MutableList<CatalogItem> {
        val sp = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sp.getString("favorites", null)
        val type = object : TypeToken<List<String>>() {}.type
        val favoriteIds: List<String> = gson.fromJson(json, type) ?: emptyList()

        // Reconstruir la lista completa
        val allItems = listOf(
            CatalogItem(
                id = getString(R.string.barbaro_id),
                nameRes = R.string.barbaro_name,
                descRes = R.string.barbaro_desc,
                longDescRes = R.string.barbaro_long_desc,
                imageResId = R.drawable.barbaro_ic
            ),
            CatalogItem(
                id = getString(R.string.bardo_id),
                nameRes = R.string.bardo_name,
                descRes = R.string.bardo_desc,
                longDescRes = R.string.bardo_long_desc,
                imageResId = R.drawable.bardo_ic
            ),
            CatalogItem(
                id = getString(R.string.brujo_id),
                nameRes = R.string.brujo_name,
                descRes = R.string.brujo_desc,
                longDescRes = R.string.brujo_long_desc,
                imageResId = R.drawable.brujo_ic
            ),
            CatalogItem(
                id = getString(R.string.clerigo_id),
                nameRes = R.string.clerigo_name,
                descRes = R.string.clerigo_desc,
                longDescRes = R.string.clerigo_long_desc,
                imageResId = R.drawable.clerigo_ic
            ),
            CatalogItem(
                id = getString(R.string.druida_id),
                nameRes = R.string.druida_name,
                descRes = R.string.druida_desc,
                longDescRes = R.string.druida_long_desc,
                imageResId = R.drawable.druida_ic
            ),
            CatalogItem(
                id = getString(R.string.explorador_id),
                nameRes = R.string.explorador_name,
                descRes = R.string.explorador_desc,
                longDescRes = R.string.explorador_long_desc,
                imageResId = R.drawable.explorador_ic
            ),
            CatalogItem(
                id = getString(R.string.guerrero_id),
                nameRes = R.string.guerrero_name,
                descRes = R.string.guerrero_desc,
                longDescRes = R.string.guerrero_long_desc,
                imageResId = R.drawable.guerrero_ic
            ),
            CatalogItem(
                id = getString(R.string.hechicero_id),
                nameRes = R.string.hechicero_name,
                descRes = R.string.hechicero_desc,
                longDescRes = R.string.hechicero_long_desc,
                imageResId = R.drawable.hechicero_ic
            ),
            CatalogItem(
                id = getString(R.string.mago_id),
                nameRes = R.string.mago_name,
                descRes = R.string.mago_desc,
                longDescRes = R.string.mago_long_desc,
                imageResId = R.drawable.mago_ic
            ),
            CatalogItem(
                id = getString(R.string.monje_id),
                nameRes = R.string.monje_name,
                descRes = R.string.monje_desc,
                longDescRes = R.string.monje_long_desc,
                imageResId = R.drawable.monje_ic
            ),
            CatalogItem(
                id = getString(R.string.paladin_id),
                nameRes = R.string.paladin_name,
                descRes = R.string.paladin_desc,
                longDescRes = R.string.paladin_long_desc,
                imageResId = R.drawable.paladin_ic
            ),
            CatalogItem(
                id = getString(R.string.picaro_id),
                nameRes = R.string.picaro_name,
                descRes = R.string.picaro_desc,
                longDescRes = R.string.picaro_long_desc,
                imageResId = R.drawable.picaro_icc
            )
        )

        allItems.forEach { item ->
            item.isFavorite = favoriteIds.contains(item.id)
        }
        return allItems.filter { it.isFavorite }.toMutableList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
