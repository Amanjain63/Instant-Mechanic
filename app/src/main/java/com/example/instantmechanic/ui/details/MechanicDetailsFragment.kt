package com.example.instantmechanic.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instantmechanic.R
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.databinding.FragmentMechanicDetailsBinding


class MechanicDetailsFragment : Fragment(R.layout.fragment_mechanic_details) {
    private var _binding: FragmentMechanicDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMechanicDetailsBinding.bind(view)

        val mechanic = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("mechanic", Mechanic::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("mechanic")
        }

        mechanic?.let {
            showMechanicDetails(it)
        }
    }

    private fun showMechanicDetails(mechanic: Mechanic) {

        binding.tvMechanicName.text = mechanic.name

        binding.tvRating.text =
            "⭐ ${mechanic.rating}"

        binding.tvLocation.text =
            "📍 ${mechanic.location}"

        binding.tvAddress.text =
            mechanic.address

        binding.tvServices.text =
            mechanic.services.joinToString(" • ")

        binding.tvWorkingHours.text =
            mechanic.workingHours

        binding.tvPhone.text =
            mechanic.phone

        binding.tvStatus.text =
            if (mechanic.open) "OPEN NOW" else "CLOSED"

        binding.btnRequestService.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("mechanic", mechanic)
            }
            val requestFragment = com.example.instantmechanic.ui.request.RequestServiceFragment().apply {
                arguments = bundle
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, requestFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}