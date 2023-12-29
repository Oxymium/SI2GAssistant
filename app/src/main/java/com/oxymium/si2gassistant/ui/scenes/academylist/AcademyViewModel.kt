package com.oxymium.si2gassistant.ui.scenes.academylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.entities.Academy
import com.oxymium.si2gassistant.domain.repository.AcademyRepository
import com.oxymium.si2gassistant.domain.usecase.AcademyListEvent
import com.oxymium.si2gassistant.ui.scenes.academylist.components.AcademyListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
class AcademyViewModel(academyRepository: AcademyRepository): ViewModel() {

    private val _state = MutableStateFlow(AcademyListState())
    private val _filter = MutableStateFlow<AcademyFilter>(AcademyFilter.Default)
    private val _selected = MutableStateFlow<Academy?>(null)

    val state = combine(
        _state, academyRepository.getAllAcademies(), _filter, _selected
    ) {  state, academies, filter, selected ->
        state.copy(
            selectedAcademy = selected,
            academyList = when (filter) {
                is AcademyFilter.Alphabetically -> if (filter.isAscendingSorting) academies.sortedBy { it.shortTitle } else academies.sortedBy { it.shortTitle }.reversed()
                is AcademyFilter.Numerically ->  if (filter.isAscendingSorting) academies.sortedBy { it.id } else academies.sortedBy { it.id }.reversed()
                AcademyFilter.Default -> academies // returns no sorting if no filter
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AcademyListState())

    fun onEvent(event: AcademyListEvent) {
        when (event) {

            is AcademyListEvent.SelectAcademy -> {
                _selected.value = event.academy
            }

            AcademyListEvent.OnAlphabeticallyFilterButtonClick -> {
                _filter.value = if (_filter.value == AcademyFilter.Alphabetically(false)) AcademyFilter.Alphabetically(true) else AcademyFilter.Alphabetically(false)
            }

            AcademyListEvent.OnNumericallyFilterButtonClick -> {
                _filter.value =  if (_filter.value == AcademyFilter.Numerically(false)) AcademyFilter.Numerically(true) else AcademyFilter.Numerically(false)

            }
        }
    }
}