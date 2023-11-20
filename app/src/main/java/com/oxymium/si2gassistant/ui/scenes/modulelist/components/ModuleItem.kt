package com.oxymium.si2gassistant.ui.scenes.modulelist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.Module
import com.oxymium.si2gassistant.domain.entities.mock.ALL_MODULES
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun ModuleItem(module: Module) {
    Box(
        modifier = Modifier
            .fillMaxWidth(9f)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(text = module.id)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModuleItemPreview() {
    val modulePreview = ALL_MODULES.random()
    Si2GAssistantTheme {
        ModuleItem(module = modulePreview)
    }
}