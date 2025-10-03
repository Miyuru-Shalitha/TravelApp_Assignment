package com.example.travelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.parent
import kotlinx.coroutines.launch

class TripPointItemAdapter(private val data: List<String>, private val fetchTripPoints: () -> Unit) :
    RecyclerView.Adapter<TripPointItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.trip_point, parent, false)

        return ViewHolder(view, fetchTripPoints)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View, val fetchTripPoints: () -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(text: String) {
            val tripPointTitleTextView =
                itemView.findViewById<TextView>(R.id.text_view_trip_point_title)
            tripPointTitleTextView.text = text
            val deleteButton = itemView.findViewById<ImageView>(R.id.image_view_delete_icon)

            deleteButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

                        if (currentUserId != null) {
                            FirebaseFirestore.getInstance().collection("users")
                                .document(currentUserId)
                                .collection("destinations").whereEqualTo("destination", text)
                                .get()
                                .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                                    val document = querySnapshot.documents.first()
                                    document.reference.delete()
                                        .addOnSuccessListener {
                                            fetchTripPoints()
                                        }
                                        .addOnFailureListener {
                                            // TODO(Miyuru): Display an error message.
                                        }
                                }
                                .addOnFailureListener {
                                    // TODO(Miyuru): Display an error message.
                                }
                        }
                    } catch (err: Exception) {
                        // TODO(Miyuru): Display an error message.
                    }
                }
            }
        }
    }
}