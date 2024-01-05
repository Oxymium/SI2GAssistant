package com.oxymium.si2gassistant.ui.scenes.reportbug.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oxymium.si2gassistant.R
import com.oxymium.si2gassistant.domain.entities.ALL_BUG_CATEGORIES
import com.oxymium.si2gassistant.domain.entities.ALL_BUG_PRIORITIES
import com.oxymium.si2gassistant.domain.entities.BugTicketCategory
import com.oxymium.si2gassistant.domain.entities.BugTicketPriority
import com.oxymium.si2gassistant.ui.scenes.animations.UploadingAnimation
import com.oxymium.si2gassistant.ui.scenes.reportbug.ReportBugEvent
import com.oxymium.si2gassistant.domain.states.ReportBugState
import com.oxymium.si2gassistant.ui.theme.MenuAccent
import com.oxymium.si2gassistant.ui.theme.Neutral
import com.oxymium.si2gassistant.ui.theme.Si2GAssistantTheme
import com.oxymium.si2gassistant.ui.theme.TextAccent
import com.oxymium.si2gassistant.utils.CapitalizeFirstLetter
import com.skydoves.orchestra.spinner.Spinner
import com.skydoves.orchestra.spinner.SpinnerProperties

@Composable
fun ReportBugScreenTest(
    state: ReportBugState,
    event: (ReportBugEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // CATEGORY
        val bugCategories = remember { ALL_BUG_CATEGORIES.map { it.name } }
        val (selectedItem1, setSelectedItem1) = remember { mutableStateOf("Pick a category") }
        Spinner(
            text = selectedItem1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Neutral,
                    shape = RoundedCornerShape(8.dp)
                ),
            itemList = bugCategories,
            style = MaterialTheme.typography.body2,
            properties = SpinnerProperties(
                color = Color.White,
                textAlign = TextAlign.Center,
                dividerColor = MenuAccent,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                spinnerPadding = 16.dp,
                spinnerBackgroundColor = Neutral,
            ),
            onSpinnerItemSelected = { index, item ->
                val capitalizedCategory = CapitalizeFirstLetter.capitalizeFirstLetter(item)
                setSelectedItem1(capitalizedCategory)
                event.invoke(ReportBugEvent.OnBugCategorySelect(BugTicketCategory.valueOf(item)))
            }
        )

        if (state.isCategoryFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*need to select a category",
                color = Color.Red
            )
        }

        // PRIORITY
        val bugPriorities = remember { ALL_BUG_PRIORITIES.map { it.name } }
        val (selectedItem2, setSelectedItem2) = remember { mutableStateOf("Pick a priority") }
        Spinner(
            text = selectedItem2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Neutral,
                    shape = RoundedCornerShape(8.dp)
                ),
            itemList = bugPriorities,
            style = MaterialTheme.typography.body2,
            properties = SpinnerProperties(
                color = Color.White,
                textAlign = TextAlign.Center,
                dividerColor = MenuAccent,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                spinnerPadding = 16.dp,
                spinnerBackgroundColor = Neutral,
            ),
            onSpinnerItemSelected = { _, item ->
                val capitalizedPriority = CapitalizeFirstLetter.capitalizeFirstLetter(item)
                setSelectedItem2(capitalizedPriority)
                event.invoke(ReportBugEvent.OnBugPrioritySelect(BugTicketPriority.valueOf(item)))
            }
        )

        if (state.isPriorityFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*need to select a priority",
                color = Color.Red
            )
        }

        // SHORT DESCRIPTION
        var shortDescription by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = shortDescription,
                onValueChange = {
                    shortDescription = it.take(100)
                    event.invoke(ReportBugEvent.OnShortDescriptionChange(shortDescription))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = TextAccent,
                    cursorColor = Color.Black,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = Color.Black
                ),
                label = {
                    Text(
                        text = "Short description",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                maxLines = 1
            )
        }

        if (state.isShortDescriptionFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*short description required",
                color = Color.Red
            )
        }

        // COMMENT
        var description by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = description,
                onValueChange = {
                    description = it.take(500)
                    event.invoke(ReportBugEvent.OnDescriptionChange(description))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = TextAccent,
                    cursorColor = Color.Black,
                    focusedBorderColor = TextAccent,
                    unfocusedBorderColor = Color.Black
                ),
                label = {
                    Text(
                        text = "Description",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                minLines = 12,
                maxLines = 12
            )
        }

        if (state.isDescriptionFieldError) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                text = "*description required",
                color = Color.Red
            )
        }

        if (state.isSubmitBugTicketLoading) {

            UploadingAnimation()

        } else {

            // PUSH TICKET BUTTON
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutral
                ),
                onClick = { event.invoke(ReportBugEvent.OnReportBugButtonClick) }
            ) {

                Icon(
                    modifier = Modifier
                        .background(Neutral)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_cloud_upload),
                    contentDescription = null,
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                )

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ReportBugScreenTestPreview() {
    Si2GAssistantTheme {
        val statePreview = ReportBugState()
        ReportBugScreenTest(
            state = statePreview
        ){}
    }
}