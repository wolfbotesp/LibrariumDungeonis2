package com.example.librariumdungeonis.ui.catalog.model

import androidx.annotation.StringRes

data class CatalogItem(
    val id: String,                // ID inmutable (por ejemplo, "barbaro")
    @StringRes val nameRes: Int,    // Recurso de cadena para el nombre (corto)
    @StringRes val descRes: Int,    // Recurso de cadena para la descripción corta (para el catálogo)
    @StringRes val longDescRes: Int, // Recurso de cadena para la descripción larga (para el detalle)
    val imageResId: Int,           // Imagen para el catálogo (por ejemplo, icono)
    var isFavorite: Boolean = false
)
