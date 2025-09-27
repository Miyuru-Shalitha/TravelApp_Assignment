package com.example.travelapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    lateinit var exploreNearbyRecyclerView: RecyclerView
    lateinit var recommendedForYou: RecyclerView

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
        exploreNearbyRecyclerView = view.findViewById(R.id.recycler_view_explore_nearby_container)
        exploreNearbyRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val nearbyPlaces = listOf(
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura."
            ),
            Place(
                R.drawable.polonnaruwa__gal_viharaya,
                "Gal Viharaya",
                "Gal Vihara, located in the northern part of the Ancient City of Polonnaruwa, is a rock-cut temple dating back to the 12th century, commissioned by King Parakramabahu I. It features four monumental Buddha statues carved directly into a single granite rock face, showcasing the pinnacle of ancient Sinhalese rock-carving artistry. These statues—a large seated Buddha, a smaller seated Buddha in a cave-like shrine, a standing Buddha with an enigmatic expression, and a 7-meter-long reclining Buddha—are among Sri Lanka’s most revered Buddhist sculptures. The serene setting, surrounded by lush greenery and ancient ruins, offers a tranquil experience for visitors seeking history, spirituality, or photography. Gal Vihara is included in the Polonnaruwa Ancient City ticket (\$25–30 USD for foreigners) and is a must-visit for its cultural and artistic significance.",
                "Gal Vihara, located in the northern part of the Ancient City of Polonnaruwa, is a rock-cut temple dating back to the 12th century, commissioned by King Parakramabahu I."
            ),
            Place(
                R.drawable.polonnaruwa__lankathilaka_temple,
                "Lankathilaka Temple",
                "Lankatilaka Temple, located in the heart of Polonnaruwa’s Ancient City, is a grand Buddhist temple renowned for its monumental architecture and serene ambiance. Constructed during the reign of Parakramabahu I (1153–1186 CE), the temple’s image house originally stood over 17 meters tall, with thick brick walls adorned with detailed stucco carvings of deities, mythical creatures, and floral motifs. The centerpiece is a colossal standing Buddha statue, now partially damaged, which once reached 13 meters in height. The temple’s narrow aisles and high vaulted ceiling create a sense of awe, while its historical significance as a center of Buddhist worship adds spiritual depth. Included in the Polonnaruwa Ancient City ticket (\$25–30 USD for foreigners), Lankatilaka is a highlight for visitors interested in ancient architecture, Buddhist heritage, and photography.",
                "Lankatilaka Temple, located in the heart of Polonnaruwa’s Ancient City, is a grand Buddhist temple renowned for its monumental architecture and serene ambiance."
            ),
            Place(
                R.drawable.polonnaruwa__parakrama_samudraya,
                "Parakrama Samudraya",
                "Parakrama Samudra, constructed during the reign of King Parakramabahu I (1153–1186 CE), is one of the largest and most impressive reservoirs in Sri Lanka, reflecting the ingenuity of ancient irrigation systems. Spanning about 25 square kilometers, this \"Sea of Parakrama\" was created by damming the Amban Ganga River and connecting smaller reservoirs to ensure a reliable water supply for Polonnaruwa’s agriculture and its thriving capital. The reservoir’s serene waters, fringed by lush greenery and distant hills, offer breathtaking views, especially at sunrise or sunset. It’s a haven for birdwatchers, with species like herons, egrets, and kingfishers, and a tranquil spot for visitors seeking nature and history. Located just outside the Ancient City, it’s accessible without the Polonnaruwa ticket, making it a budget-friendly stop. The reservoir’s historical role in sustaining Polonnaruwa’s golden era adds depth to its appeal for cultural explorers.",
                "Parakrama Samudra, constructed during the reign of King Parakramabahu I (1153–1186 CE), is one of the largest and most impressive reservoirs in Sri Lanka."
            ),
            Place(
                R.drawable.polonnaruwa__rankoth_vehera,
                "Rankoth Vehera",
                "Rankoth Vehera is the largest stupa in Polonnaruwa, Sri Lanka, and a prominent highlight of the Ancient City of Polonnaruwa, a UNESCO World Heritage Site. Built in the 12th century by King Nissanka Malla, this majestic brick stupa stands 55 meters tall with a circumference of 185 meters, symbolizing the grandeur of ancient Sinhalese architecture. Its name, meaning \"Golden Pinnacle,\" reflects the once-gilded spire that crowned the structure. Surrounded by a spacious terrace and four entrance shrines (vahalkadas), Rankoth Vehera remains a significant Buddhist monument, exuding a sense of reverence and history. The stupa’s imposing dome, set amidst Polonnaruwa’s ruins and greenery, offers panoramic views and a serene atmosphere, making it a must-visit for history enthusiasts and spiritual travelers. Accessible via the Polonnaruwa Ancient City ticket (\$25–30 USD for foreigners), it’s a short walk or bike ride from sites like Gal Vihara or Lankatilaka Temple.",
                "Rankoth Vehera is the largest stupa in Polonnaruwa, Sri Lanka, and a prominent highlight of the Ancient City of Polonnaruwa, a UNESCO World Heritage Site. Built in the 12th century by King Nissanka Malla, this majestic brick stupa stands 55 meters tall with a circumference of 185 meters, symbolizing the grandeur of ancient Sinhalese architecture."
            ),
            Place(
                R.drawable.polonnaruwa__royal_palace_of_king_parakramabahu,
                "Royal Palace of King Parakramabahu",
                "Royal Palace of King Parakramabahu, located in the heart of the Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka, is a striking testament to the grandeur of the 12th-century Sinhalese monarchy. Constructed during the reign of King Parakramabahu I (1153–1186 CE), this once-magnificent seven-story palace, known as Vijayanta Prasada, was a central hub of royal administration and opulence. The surviving ruins, primarily the lower levels with thick brick walls and remnants of 40 rooms, showcase intricate stonework and architectural sophistication. Originally adorned with ornate decorations, the palace featured grand halls, staircases, and upper floors with panoramic views of Polonnaruwa. Surrounded by the ancient city’s lush greenery and nearby sites like the Vatadage, it offers a glimpse into the regal life of one of Sri Lanka’s greatest kings. Accessible via the Polonnaruwa Ancient City ticket (\$25–30 USD for foreigners), it’s a must-visit for history and architecture enthusiasts.",
                "Royal Palace of King Parakramabahu, located in the heart of the Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka, is a striking testament to the grandeur of the 12th-century Sinhalese monarchy."
            ),
            Place(
                R.drawable.polonnaruwa__vatadage,
                "Polonnaruwa Vatadage",
                "Polonnaruwa Vatadage, a magnificent circular relic house in the Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka, is a masterpiece of 12th-century Sinhalese architecture. Built during the reign of King Parakramabahu I (1153–1186 CE) and later enhanced by King Nissanka Malla, this elegant structure is believed to have once housed the Sacred Tooth Relic of the Buddha. The Vatadage features a central stupa surrounded by two concentric stone platforms, adorned with intricately carved moonstones, guardstones, and 32 stone columns that once supported a wooden roof. Its detailed carvings, including floral motifs and mythical figures, showcase the artistic brilliance of the era. Set within the sacred quadrangle of Polonnaruwa, near sites like Gal Vihara, it offers a serene and historically rich experience for visitors. Accessible via the Polonnaruwa Ancient City ticket (\$25–30 USD for foreigners), it’s a highlight for those exploring Buddhist heritage and ancient craftsmanship.",
                "Polonnaruwa Vatadage, a magnificent circular relic house in the Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka, is a masterpiece of 12th-century Sinhalese architecture."
            ),
            Place(
                R.drawable.polonnaruwa__wasgamuwa_national_park,
                "Wasgamuwa National Park",
                "Wasgamuwa National Park, located about an hour’s drive from Polonnaruwa in Sri Lanka’s North Central Province, is a pristine wildlife sanctuary spanning over 370 square kilometers. Established in 1984, this biodiverse park is renowned for its rich ecosystem, featuring dry-zone evergreen forests, grasslands, and the Mahaweli River. It’s a haven for wildlife enthusiasts, hosting a variety of species, including Sri Lankan elephants, leopards, sloth bears, sambar deer, and over 140 bird species like the endemic red-faced malkoha. The park’s rugged terrain and ancient irrigation tanks, such as Wasgamuwa Tank, add historical depth, with remnants linked to the Polonnaruwa era. Offering jeep safaris and birdwatching tours, it provides an adventurous escape into nature, contrasting with Polonnaruwa’s historical ruins. Its serene landscapes and wildlife sightings make it a must-visit for eco-tourists.",
                "Wasgamuwa National Park, located about an hour’s drive from Polonnaruwa in Sri Lanka’s North Central Province, is a pristine wildlife sanctuary spanning over 370 square kilometers."
            )
        )
        exploreNearbyRecyclerView.adapter = PlacePreviewItemAdapter(nearbyPlaces)

        recommendedForYou = view.findViewById(R.id.recycler_view_recommended_for_you)
        recommendedForYou.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val recommendedPlaces = listOf(
            Place(
                R.drawable.polonnaruwa__ancient_city,
                "Ancient City",
                "Polonnaruwa served as Sri Lanka’s capital during a golden age, flourishing under King Parakramabahu I (1153–1186 CE), who transformed it into a thriving hub of culture, religion, and engineering. The city’s ruins, remarkably well-preserved, reflect a sophisticated urban layout with advanced irrigation systems, such as the massive Parakrama Samudra reservoir. Visitors can explore a compact yet rich collection of archaeological sites, including palaces, temples, and stupas, many accessible via a single entry ticket (around \$25–30 USD for foreigners). The site’s serene ambiance, punctuated by ancient structures and wandering monkeys, makes it ideal for history buffs, photographers, and cultural explorers. Cycling through the ruins is a popular way to experience the sprawling complex, with key highlights like Gal Vihara and the Vatadage drawing global travelers.",
                "The Ancient City of Polonnaruwa, a UNESCO World Heritage Site in Sri Lanka’s North Central Province, was the island’s second capital from the 11th to 13th centuries, following Anuradhapura."
            )
        )
        recommendedForYou.adapter = PlacePreviewItemAdapter(recommendedPlaces)
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
}
