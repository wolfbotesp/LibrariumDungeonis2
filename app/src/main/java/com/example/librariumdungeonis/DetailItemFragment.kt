package com.example.librariumdungeonis.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.librariumdungeonis.R
import com.example.librariumdungeonis.ui.catalog.model.CatalogItem

class DetailItemFragment : Fragment(R.layout.fragment_detail_item) {

    private val args: DetailItemFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailImageView = view.findViewById<ImageView>(R.id.detailImageView)
        val detailNameTextView = view.findViewById<TextView>(R.id.detailNameTextView)
        val detailDescriptionTextView = view.findViewById<TextView>(R.id.detailDescriptionTextView)
        val btnBack = view.findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val itemId = args.itemId

        // Lista completa de 12 clases, usando la descripción larga
        val allItems = listOf(
            CatalogItem(
                id = getString(R.string.barbaro_id),
                nameRes = R.string.barbaro_name,
                descRes = R.string.barbaro_desc,         // no se usará aquí
                longDescRes = R.string.barbaro_long_desc,
                imageResId = R.drawable.barbaro
            ),
            CatalogItem(
                id = getString(R.string.bardo_id),
                nameRes = R.string.bardo_name,
                descRes = R.string.bardo_desc,
                longDescRes = R.string.bardo_long_desc,
                imageResId = R.drawable.bardo
            ),
            CatalogItem(
                id = getString(R.string.brujo_id),
                nameRes = R.string.brujo_name,
                descRes = R.string.brujo_desc,
                longDescRes = R.string.brujo_long_desc,
                imageResId = R.drawable.brujo
            ),
            CatalogItem(
                id = getString(R.string.clerigo_id),
                nameRes = R.string.clerigo_name,
                descRes = R.string.clerigo_desc,
                longDescRes = R.string.clerigo_long_desc,
                imageResId = R.drawable.clerigo
            ),
            CatalogItem(
                id = getString(R.string.druida_id),
                nameRes = R.string.druida_name,
                descRes = R.string.druida_desc,
                longDescRes = R.string.druida_long_desc,
                imageResId = R.drawable.druida
            ),
            CatalogItem(
                id = getString(R.string.explorador_id),
                nameRes = R.string.explorador_name,
                descRes = R.string.explorador_desc,
                longDescRes = R.string.explorador_long_desc,
                imageResId = R.drawable.explorador
            ),
            CatalogItem(
                id = getString(R.string.guerrero_id),
                nameRes = R.string.guerrero_name,
                descRes = R.string.guerrero_desc,
                longDescRes = R.string.guerrero_long_desc,
                imageResId = R.drawable.guerrero
            ),
            CatalogItem(
                id = getString(R.string.hechicero_id),
                nameRes = R.string.hechicero_name,
                descRes = R.string.hechicero_desc,
                longDescRes = R.string.hechicero_long_desc,
                imageResId = R.drawable.hechicero
            ),
            CatalogItem(
                id = getString(R.string.mago_id),
                nameRes = R.string.mago_name,
                descRes = R.string.mago_desc,
                longDescRes = R.string.mago_long_desc,
                imageResId = R.drawable.mago
            ),
            CatalogItem(
                id = getString(R.string.monje_id),
                nameRes = R.string.monje_name,
                descRes = R.string.monje_desc,
                longDescRes = R.string.monje_long_desc,
                imageResId = R.drawable.monje
            ),
            CatalogItem(
                id = getString(R.string.paladin_id),
                nameRes = R.string.paladin_name,
                descRes = R.string.paladin_desc,
                longDescRes = R.string.paladin_long_desc,
                imageResId = R.drawable.paladin
            ),
            CatalogItem(
                id = getString(R.string.picaro_id),
                nameRes = R.string.picaro_name,
                descRes = R.string.picaro_desc,
                longDescRes = R.string.picaro_long_desc,
                imageResId = R.drawable.picaro
            )
        )

        val selectedItem = allItems.firstOrNull { it.id == itemId }

        if (selectedItem != null) {
            val nameText = getString(selectedItem.nameRes)
            val longDescText = getString(selectedItem.longDescRes)
            detailNameTextView.text = nameText
            detailDescriptionTextView.text = longDescText

            Glide.with(this)
                .load(selectedItem.imageResId)
                .into(detailImageView)
        } else {
            Toast.makeText(
                requireContext(),
                "No se encontró la clase con ID: $itemId",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }
    }
}
