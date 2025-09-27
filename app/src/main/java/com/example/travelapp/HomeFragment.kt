package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.osmdroid.api.IGeoPoint
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var exploreNearbyRecyclerView: RecyclerView
    private lateinit var recommendedForYou: RecyclerView

    private lateinit var locationProvider: GpsMyLocationProvider
    private lateinit var places: List<Place>
    private var isDataFetchingFinished: Boolean = false
    private var isFilteringFinished: Boolean = false

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationProvider = GpsMyLocationProvider(context)
        locationProvider.addLocationSource(android.location.LocationManager.GPS_PROVIDER)
        locationProvider.addLocationSource(android.location.LocationManager.NETWORK_PROVIDER)

        var latitude: Double = 0.0
        var longitude: Double = 0.0

        locationProvider.startLocationProvider { location, source ->
            latitude = location.latitude
            longitude = location.longitude
//            val accuracy = location.accuracy
//            val altitude = location.altitude

            if (isDataFetchingFinished && !isFilteringFinished) {
                filterNearbyPlaces(latitude, longitude)
                isFilteringFinished = true
            }
        }

        exploreNearbyRecyclerView = view.findViewById(R.id.recycler_view_explore_nearby_container)
        exploreNearbyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val snapshot = FirebaseFirestore.getInstance().collection("places").get().await()
                places = snapshot.documents.mapNotNull { doc ->
                    val imageId = doc.getString("imageId") ?: return@mapNotNull null
                    val name = doc.getString("name") ?: return@mapNotNull null
                    val description = doc.getString("description") ?: return@mapNotNull null
                    val shortDescription =
                        doc.getString("shortDescription") ?: return@mapNotNull null

                    val resourceId =
                        resources.getIdentifier(imageId, "drawable", context?.packageName)
                    val position = doc.getGeoPoint("location") ?: return@mapNotNull null
                    Place(
                        resourceId,
                        name,
                        description,
                        shortDescription,
                        position.latitude,
                        position.longitude
                    )
                }

                isDataFetchingFinished = true
            } catch (err: Exception) {
                Toast.makeText(context, "Failed to load nearby places!", Toast.LENGTH_SHORT).show()
            }
        }

        recommendedForYou = view.findViewById(R.id.recycler_view_recommended_for_you)
        recommendedForYou.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val recommendedPlaces = listOf(
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura.",
                0.0,
                0.0
            ),
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura.",
                0.0,
                0.0
            ),
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura.",
                0.0,
                0.0
            ),
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura.",
                0.0,
                0.0
            ),
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura.",
                0.0,
                0.0
            ),
        )
        recommendedForYou.adapter = PlacePreviewItemAdapter(recommendedPlaces) { place: Place ->
            val placeInfoIntent = Intent(context, PlaceInfoActivity::class.java).apply {
                putExtra("imageId", place.imageId)
                putExtra("name", place.name)
                putExtra("description", place.description)
                putExtra("shortDescription", place.shortDescription)
                putExtra("latitude", place.latitude)
                putExtra("longitude", place.longitude)
            }
            startActivity(placeInfoIntent)
        }
    }

    override fun onPause() {
        super.onPause()
        locationProvider.stopLocationProvider()
    }

    private fun filterNearbyPlaces(latitude: Double, longitude: Double) {
        val myPosition = GeoPoint(latitude, longitude)

        val nearbyPlaces = places.filter {
            val distance =
                myPosition.distanceToAsDouble(GeoPoint(it.latitude, it.longitude))
            distance < 10000.0
        }

        exploreNearbyRecyclerView.adapter = PlacePreviewItemAdapter(nearbyPlaces) { place: Place ->
            val placeInfoIntent = Intent(context, PlaceInfoActivity::class.java).apply {
                putExtra("imageId", place.imageId)
                putExtra("name", place.name)
                putExtra("description", place.description)
                putExtra("shortDescription", place.shortDescription)
                putExtra("latitude", place.latitude)
                putExtra("longitude", place.longitude)
            }
            startActivity(placeInfoIntent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun saySomething() {
        Toast.makeText(context, "SOMETHING!!!!!", Toast.LENGTH_SHORT).show()
    }
}
