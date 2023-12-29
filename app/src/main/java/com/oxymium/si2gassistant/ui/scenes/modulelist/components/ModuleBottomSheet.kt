package com.oxymium.si2gassistant.ui.scenes.modulelist.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oxymium.si2gassistant.ui.scenes.academylist.components.ModuleListState
import com.oxymium.si2gassistant.ui.theme.Neutral

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleBottomSheet(
    state: ModuleListState,
) {

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize(0.95f),
        containerColor = Neutral,
        onDismissRequest = { Unit }
    ) {

        Text(text = "CLICKED")

    }

}