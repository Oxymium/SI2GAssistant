package com.oxymium.si2gassistant.domain.entities.mock

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.oxymium.si2gassistant.domain.entities.Module

val ALL_MODULES = listOf(
    Module(
        "01",
        "1 # Introduction and Onboarding",
        LoremIpsum(10).toString()
    ),
    Module(
        "02",
        "2 # User Registration and Account Setup",
        LoremIpsum(10).toString()
    ),
    Module(
        "03",
        "3 # Exploring App Features",
        LoremIpsum(10).toString()
    ),
    Module(
        "04",
        "4 # Customizing Preferences",
        LoremIpsum(10).toString()
    ),
    Module(
        "05",
        "5 # Engaging with Content",
        LoremIpsum(10).toString()
    ),
    Module(
        "06",
        "6 # Social Interactions and Networking",
        LoremIpsum(10).toString()
    ),
    Module(
        "07",
        "7 # Notifications and Updates",
        LoremIpsum(10).toString()
    ),
    Module(
        "08",
        "8 # Feedback and Support",
        LoremIpsum(10).toString()
    )
)