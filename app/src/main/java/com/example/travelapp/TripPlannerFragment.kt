package com.example.travelapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.jetbrains.annotations.Async.Execute

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TripPlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TripPlannerFragment : Fragment() {
    lateinit var tripPlannerRecyclerView: RecyclerView

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
        return inflater.inflate(R.layout.fragment_trip_planner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tripPlannerRecyclerView = view.findViewById(R.id.recycler_view_trip_point_container)
        tripPlannerRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fetchTripPoints()
    }

    fun fetchTripPoints() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val snapshot = FirebaseFirestore.getInstance().collection("users")
                        .document(currentUser.uid)
                        .collection("destinations").get().await()

                    val destinationList = snapshot.documents.mapNotNull { doc ->
                        val destination = doc.getString("destination") ?: return@mapNotNull null
                        destination
                    }

                    tripPlannerRecyclerView.adapter =
                        TripPointItemAdapter(destinationList, { fetchTripPoints() })
                } catch (err: Exception) {
//                    Toast(context, "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TripPlannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TripPlannerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}