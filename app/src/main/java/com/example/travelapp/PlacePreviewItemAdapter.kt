package com.example.travelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacePreviewItemAdapter(private val data: List<Place>) :
    RecyclerView.Adapter<PlacePreviewItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_preview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var placeImageImageView: ImageView =
            itemView.findViewById(R.id.image_view_place_preview)
        private var cityNameTextView: TextView = itemView.findViewById(R.id.text_view_place_name)
        private var descriptionTextView: TextView =
            itemView.findViewById(R.id.text_view_place_description)

        fun bind(place: Place) {
            placeImageImageView.setImageResource(place.imageId)
            cityNameTextView.text = place.name
            descriptionTextView.text = place.shortDescription
        }
    }
}