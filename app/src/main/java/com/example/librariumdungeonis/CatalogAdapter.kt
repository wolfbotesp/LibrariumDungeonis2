package com.example.librariumdungeonis.ui.catalog.adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librariumdungeonis.R
import com.example.librariumdungeonis.ui.catalog.model.CatalogItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CatalogAdapter(
    private val context: Context,
    private val catalogList: MutableList<CatalogItem>
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    init {
        loadFavorites()
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        val nameTextView: TextView = itemView.findViewById(R.id.itemName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.itemDescription)
        val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalog, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val item = catalogList[position]

        Glide.with(holder.imageView.context)
            .load(item.imageResId)
            .into(holder.imageView)

        val nameText = holder.itemView.context.getString(item.nameRes)
        val descText = holder.itemView.context.getString(item.descRes)

        holder.nameTextView.text = nameText
        holder.descriptionTextView.text = descText
        updateFavoriteIcon(holder.btnFavorite, item.isFavorite)

        holder.btnFavorite.setOnClickListener {
            item.isFavorite = !item.isFavorite
            updateFavoriteIcon(holder.btnFavorite, item.isFavorite)
            saveFavorites()
            if (item.isFavorite) {
                val msg = holder.itemView.context.getString(R.string.added_to_favorites, nameText)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            } else {
                val msg = holder.itemView.context.getString(R.string.removed_from_favorites, nameText)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }

        // Navegar al detalle: pasar el id inmutable
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("itemId", item.id)
            }
            it.findNavController().navigate(R.id.detailItemFragment, bundle)
        }
    }

    override fun getItemCount(): Int = catalogList.size

    private fun updateFavoriteIcon(button: ImageButton, isFavorite: Boolean) {
        button.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )
    }

    private fun saveFavorites() {
        val editor = sharedPreferences.edit()
        val favoriteIds = catalogList.filter { it.isFavorite }.map { it.id }
        editor.putString("favorites", gson.toJson(favoriteIds))
        editor.apply()
    }

    private fun loadFavorites() {
        val json = sharedPreferences.getString("favorites", null)
        val type = object : TypeToken<List<String>>() {}.type
        val favoriteIds: List<String> = gson.fromJson(json, type) ?: emptyList()
        catalogList.forEach { it.isFavorite = favoriteIds.contains(it.id) }
    }
}
