package com.oxymium.si2gassistant.ui.scenes.persons.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.mock.provideRandomPerson
import com.oxymium.si2gassistant.ui.scenes.submitperson.SubmitPersonEvent
import com.oxymium.si2gassistant.ui.theme.NotValidatedModule
import com.oxymium.si2gassistant.ui.theme.Person1
import com.oxymium.si2gassistant.ui.theme.Person2
import com.oxymium.si2gassistant.ui.theme.Person3
import com.oxymium.si2gassistant.ui.theme.Person4
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.ValidatedModule
import com.oxymium.si2gassistant.ui.theme.White
import com.oxymium.si2gassistant.ui.theme.White75

@Composable
fun PersonItem(
    index: Int,
    person: Person,
    event: (SubmitPersonEvent) -> Unit
) {

    val colors = listOf(Person1, Person2, Person3, Person4)
    val colorIndex = index % colors.size // modulo
    val backgroundColor = colors[colorIndex] // colorIndex to get color from list

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        // PERSON
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = backgroundColor,
                    shape = MaterialTheme.shapes.medium
                )
                .clickable { event.invoke(SubmitPersonEvent.OnPersonSelect(person)) }
        ) {

            Box(
                modifier = Modifier
                    .rotate(180f)
                    .matchParentSize()
                    .wrapContentHeight()
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.grid_hexagons_items),
                    colorFilter = ColorFilter.tint(White75),
                    contentDescription = ""
                )
            }

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {

                // SUBMITTED BY
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    text = "${person.submittedBy}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // ROLE
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    text = person.role ?: "",
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )

                // FIRSTNAME
                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    text = person.firstname.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )

                // LASTNAME
                Text(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    text = person.lastname.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )

                // ACADEMY
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .wrapContentSize()
                            .background(White75, RoundedCornerShape(20.dp))
                            .align(Alignment.Center)
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            text = person.academy.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

        }

        // VALIDATED MODULES
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(
                    vertical = 2.dp
                )
        ) {

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {

                val validatedModulesList =
                    person.validatedModules?.split(".")?.map { it.trim() }

                for (i in 1..8) {
                    val moduleNumber = i.toString()
                    val isInValidatedList = validatedModulesList?.contains(moduleNumber) == true

                    Box(
                        modifier = Modifier
                            .background(
                                color = White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(
                                    horizontal = 8.dp
                                )
                                .background(
                                    color = backgroundColor,
                                    shape = CircleShape
                                )
                        ) {

                            Text(
                                text = moduleNumber,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .drawBehind {
                                        drawCircle(
                                            color = if (isInValidatedList) ValidatedModule else NotValidatedModule,
                                            radius = this.size.maxDimension / 2.0f
                                        )
                                    }
                                    .padding(4.dp)
                            )

                        }

                    }
                }

            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun PersonItemTestPreview() {
    Si2GAssistantTheme {
        val randomPerson = provideRandomPerson()
        PersonItem(
            index = (1..4).random(),
            person = randomPerson
        ){ }
    }
}