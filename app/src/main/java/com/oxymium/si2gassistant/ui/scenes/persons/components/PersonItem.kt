package com.oxymium.si2gassistant.ui.scenes.persons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.ui.theme.GreenTer
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme

@Composable
fun PersonItem(
    person: Person,
    event: (PersonListEvent) -> Unit
){

    Column(
      modifier = Modifier
          .fillMaxSize()
          .padding(8.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally)
                .background(
                    color = GreenTer,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { event.invoke(PersonListEvent.OnPersonClick(person)) }
        ) {

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {

                Text(text = "${person.lastname}")
                Text(text = "${person.firstname}")
                Text(text = "${person.academy}")

            }
        }

        Box(
            modifier = Modifier
                .padding(1.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            Row(
                modifier = Modifier
            ){
                val validatedModulesList = person.validatedModules?.split(", ")?.map { it.trim() }

                for (i in 1..8) {
                    val moduleNumber = i.toString()
                    val isInValidatedList = validatedModulesList?.contains(moduleNumber) == true

                    Text(
                        text = moduleNumber,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(if (isInValidatedList) Color.Green else Color.Red)
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PersonItemPreview(){
    val personPreview = Person(null, null, "Nicolas", "Doe", "1, 3, 7", "Grenoble", "", null, null)
    Si2GAssistantTheme {
        PersonItem(person = personPreview) {
        }
    }
}