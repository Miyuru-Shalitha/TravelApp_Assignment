package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val homeFragment = HomeFragment()
        val tripPlannerFragment = TripPlannerFragment()
        val mapFragment = MapFragment()
        val profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout_fragment_container, homeFragment)
            .commit()

        findViewById<ImageView>(R.id.image_view_home_tab_icon).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_fragment_container, homeFragment)
                .commit()
        }

        findViewById<ImageView>(R.id.image_view_trip_planner_tab_icon).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_fragment_container, tripPlannerFragment)
                .commit()
        }

        findViewById<ImageView>(R.id.image_view_map_tab_icon).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_fragment_container, mapFragment)
                .commit()
        }

        findViewById<ImageView>(R.id.image_view_profile_tab_icon).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_fragment_container, profileFragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            val logInIntent = Intent(this, LogInActivity::class.java)
            startActivity(logInIntent)
        }
    }
}