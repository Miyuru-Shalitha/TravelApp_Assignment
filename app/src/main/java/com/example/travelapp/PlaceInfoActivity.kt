package com.example.travelapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlaceInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_place_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageId = intent.extras?.getInt("imageId")
        val name = intent.extras?.getString("name")
        val description = intent.extras?.getString("description")
        val shortDescription = intent.extras?.getString("shortDescription")

        val placeImageImageView = findViewById<ImageView>(R.id.image_view_place_image)
        val nameTextView = findViewById<TextView>(R.id.text_view_place_name)
        val reviewStarsTextView = findViewById<TextView>(R.id.text_view_place_review_stars)
        val descriptionTextView = findViewById<TextView>(R.id.text_view_place_description)

        if (imageId != null) {
            placeImageImageView.setImageResource(imageId)
        }

        nameTextView.text = name
        descriptionTextView.text = description
    }
}