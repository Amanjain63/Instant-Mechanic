package com.example.instantmechanic.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instantmechanic.R
import com.example.instantmechanic.databinding.FragmentHomeBinding
import com.example.instantmechanic.ui.details.MechanicDetailsFragment
import com.example.instantmechanic.utils.UiState
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: MechanicAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()

        observeMechanics()

        viewModel.loadMechanics()
        setupSearch()
        setupServiceFilter()
    }

    private fun setupServiceFilter() {

        val services = listOf(
            "All Services",
            "Oil Change",
            "Brake Repair",
            "Battery",
            "AC Repair",
            "Engine Repair",
            "Tyre Service",
            "Car Wash",
            "Wheel Alignment"
        )

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            services
        )

        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinnerService.adapter =
            spinnerAdapter

        binding.spinnerService.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    viewModel.filterByService(
                        services[position]
                    )
                }

                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {
                }
            }
    }

    private fun setupSearch() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                viewModel.searchMechanics(
                    s?.toString().orEmpty()
                )
            }

            override fun afterTextChanged(
                s: Editable?
            ) {
            }
        })
    }

    private fun setupRecyclerView() {

        adapter = MechanicAdapter(emptyList()){mechanic ->
            val bundle = Bundle().apply {
                putParcelable("mechanic", mechanic)
            }

            val detailsFragment =
                MechanicDetailsFragment().apply {
                    arguments = bundle
                }

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    detailsFragment
                )
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewMechanics.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerViewMechanics.adapter = adapter
    }

    private fun observeMechanics() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.mechanicState.collect { state ->

                when (state) {

                    is UiState.Loading -> {

                        binding.progressBar.visibility =
                            View.VISIBLE

                        binding.recyclerViewMechanics.visibility =
                            View.GONE

                        binding.tvError.visibility =
                            View.GONE
                    }

                    is UiState.Success -> {

                        binding.progressBar.visibility =
                            View.GONE

                        binding.recyclerViewMechanics.visibility =
                            View.VISIBLE

                        binding.tvError.visibility =
                            View.GONE

                        // Yahan API ka data adapter ko denge
                        adapter.updateData(state.data)
                    }

                    is UiState.Error -> {

                        binding.progressBar.visibility =
                            View.GONE

                        binding.recyclerViewMechanics.visibility =
                            View.GONE

                        binding.tvError.visibility =
                            View.VISIBLE

                        binding.tvError.text =
                            state.message
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}