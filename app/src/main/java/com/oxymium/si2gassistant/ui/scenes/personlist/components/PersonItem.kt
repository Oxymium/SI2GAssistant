package com.oxymium.si2gassistant.ui.scenes.personlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun PersonItem(person: Person){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(text = "${person.id}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonItemPreview(){
    val personPreview = Person("10L")
    Si2GAssistantTheme {
        PersonItem(person = personPreview)
    }
}