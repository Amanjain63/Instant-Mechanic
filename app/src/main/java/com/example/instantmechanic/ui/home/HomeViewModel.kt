package com.example.instantmechanic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.data.repository.MechanicRepository
import com.example.instantmechanic.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val repository = MechanicRepository()
    private val _mechanicsState = MutableStateFlow<UiState<List<Mechanic>>>(UiState.Loading)
    val mechanicState: StateFlow<UiState<List<Mechanic>>> = _mechanicsState
    private var allMechanics: List<Mechanic> = emptyList()

    private var currentSearchQuery = ""

    private var currentService = "All Services"
    fun loadMechanics(){
        viewModelScope.launch {
            _mechanicsState.value = UiState.Loading
            val result = repository.getMechanic()
            result.onSuccess {
                allMechanics = it
                _mechanicsState.value = UiState.Success(it)
            }.onFailure {
                _mechanicsState.value = UiState.Error(it.message ?: "Something went wrong")
            }
        }
    }
    fun searchMechanics(query: String) {

        currentSearchQuery = query

        applyFilters()
    }
    fun filterByService(service: String) {

        currentService = service

        applyFilters()
    }
    private fun applyFilters() {

        val filteredList = allMechanics.filter { mechanic ->

            val matchesSearch =
                currentSearchQuery.isBlank() ||
                        mechanic.name.contains(
                            currentSearchQuery,
                            ignoreCase = true
                        ) ||
                        mechanic.location.contains(
                            currentSearchQuery,
                            ignoreCase = true
                        ) ||
                        mechanic.services.any {
                            it.contains(
                                currentSearchQuery,
                                ignoreCase = true
                            )
                        }

            val matchesService =
                currentService == "All Services" ||
                        mechanic.services.any {
                            it.equals(
                                currentService,
                                ignoreCase = true
                            )
                        }

            matchesSearch && matchesService
        }

        _mechanicsState.value =
            UiState.Success(filteredList)
    }
}