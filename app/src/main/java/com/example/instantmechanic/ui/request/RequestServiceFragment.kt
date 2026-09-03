package com.example.instantmechanic.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.instantmechanic.R
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.databinding.FragmentRequestServiceBinding

class RequestServiceFragment : Fragment() {

    private var _binding: FragmentRequestServiceBinding? = null
    private val binding get() = _binding!!

    private var mechanic: Mechanic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mechanic = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("mechanic", Mechanic::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("mechanic")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mechanic?.let { mech ->
            binding.tvGarageTitle.text = "Booking for: ${mech.name}"

            val servicesAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                mech.services
            )
            servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerServices.adapter = servicesAdapter
        }

        binding.btnSubmitRequest.setOnClickListener {
            submitRequest()
        }
    }

    private fun submitRequest() {
        val customerName = binding.etCustomerName.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val vehicleNumber = binding.etVehicleNumber.text.toString().trim()
        val problemDesc = binding.etProblemDescription.text.toString().trim()
        val selectedService = binding.spinnerServices.selectedItem?.toString() ?: "General Repair"

        if (customerName.isEmpty()) {
            binding.etCustomerName.error = "Please enter customer name"
            return
        }
        if (phoneNumber.isEmpty()) {
            binding.etPhoneNumber.error = "Please enter phone number"
            return
        }
        if (vehicleNumber.isEmpty()) {
            binding.etVehicleNumber.error = "Please enter vehicle number"
            return
        }
        if (problemDesc.isEmpty()) {
            binding.etProblemDescription.error = "Please describe the problem"
            return
        }

        val garageName = mechanic?.name ?: "Mechanic"

        AlertDialog.Builder(requireContext())
            .setTitle("Service Request Submitted")
            .setMessage("Your service request has been successfully sent to $garageName!\n\n" +
                    "Customer: $customerName\n" +
                    "Phone: $phoneNumber\n" +
                    "Vehicle: $vehicleNumber\n" +
                    "Service: $selectedService\n" +
                    "Problem: $problemDesc")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                parentFragmentManager.popBackStack()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
