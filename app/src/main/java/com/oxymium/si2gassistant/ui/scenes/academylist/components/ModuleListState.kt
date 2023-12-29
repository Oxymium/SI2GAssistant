package com.oxymium.si2gassistant.ui.scenes.academylist.components

import com.oxymium.si2gassistant.domain.entities.Module

data class ModuleListState(
    val moduleList: List<Module> = emptyList(),
)