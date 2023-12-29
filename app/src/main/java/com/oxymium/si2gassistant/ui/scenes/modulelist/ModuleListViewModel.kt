package com.oxymium.si2gassistant.ui.scenes.modulelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oxymium.si2gassistant.domain.repository.ModuleRepository
import com.oxymium.si2gassistant.ui.scenes.academylist.components.ModuleListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ModuleListViewModel(moduleRepository: ModuleRepository): ViewModel() {

    private val _state = MutableStateFlow(ModuleListState())

    val state = combine(
        _state, moduleRepository.getAllModules()
    ) { state, modules ->

        state.copy(
            moduleList = modules
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ModuleListState())


}