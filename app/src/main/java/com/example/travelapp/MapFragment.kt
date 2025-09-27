package com.example.travelapp

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment() {
    lateinit var mapMapView: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay

    private var param1: String? = null
    private var param2: String? = null

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("param1")
            param2 = it.getString("param2")
        }
        // Initialize osmdroid configuration
        Configuration.getInstance().load(requireContext(), requireContext().getSharedPreferences("osmdroid", MODE_PRIVATE))
        // Set User-Agent to prevent grid issue
        Configuration.getInstance().userAgentValue = "com.example.travelapp"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize MapView
        mapMapView = view.findViewById(R.id.map_view_map)

        // Set margins programmatically
        val params = mapMapView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
        val marginPx = dpToPx(16f)
        params.setMargins(marginPx, marginPx, marginPx, marginPx)
        mapMapView.layoutParams = params

        // Configure MapView
        mapMapView.apply {
            setTileSource(TileSourceFactory.MAPNIK) // Set OSM tile source
            setMultiTouchControls(true) // Enable zoom/pan
            controller.setZoom(12.0) // Initial zoom
            controller.setCenter(GeoPoint(-33.8688, 151.2093)) // Center on Sydney
        }

        // Initialize MyLocationNewOverlay
        myLocationOverlay = MyLocationNewOverlay(mapMapView)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()

        // Request location permissions
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            mapMapView.overlays.add(myLocationOverlay)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            myLocationOverlay.enableMyLocation()
            myLocationOverlay.enableFollowLocation()
            mapMapView.overlays.add(myLocationOverlay)
        }
    }

    override fun onResume() {
        super.onResume()
        mapMapView.onResume()
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myLocationOverlay.enableMyLocation()
        }
    }

    override fun onPause() {
        super.onPause()
        mapMapView.onPause()
        myLocationOverlay.disableMyLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // No need to nullify mapMapView (handled by osmdroid lifecycle)
    }

    private fun dpToPx(dp: Float): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}