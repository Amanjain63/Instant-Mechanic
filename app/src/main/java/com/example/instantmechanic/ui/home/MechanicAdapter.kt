package com.example.instantmechanic.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.databinding.ItemMechanicBinding

class MechanicAdapter(
    private var mechanics: List<Mechanic> = emptyList(),
    private val onMechanicClick: (Mechanic) -> Unit
) : RecyclerView.Adapter<MechanicAdapter.MechanicViewHolder>() {

    class MechanicViewHolder(
        private val binding: ItemMechanicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(
            mechanic: Mechanic,
            onMechanicClick: (Mechanic) -> Unit
        ) {

            binding.tvMechanicName.text = mechanic.name

            binding.tvRating.text =
                "⭐ ${mechanic.rating}"

            binding.tvLocation.text =
                "📍 ${mechanic.location}"

            binding.tvDistance.text =
                "🚗 ${mechanic.distance}"

            binding.tvServices.text =
                mechanic.services.joinToString(" • ")

            binding.tvStatus.text =
                if (mechanic.open) {
                    "OPEN"
                } else {
                    "CLOSED"
                }

            // Card click
            binding.root.setOnClickListener {
                onMechanicClick(mechanic)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MechanicViewHolder {

        val binding = ItemMechanicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MechanicViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MechanicViewHolder,
        position: Int
    ) {

        val mechanic = mechanics[position]

        holder.bind(
            mechanic,
            onMechanicClick
        )
    }

    override fun getItemCount(): Int {
        return mechanics.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMechanics: List<Mechanic>) {

        mechanics = newMechanics

        notifyDataSetChanged()
    }
}