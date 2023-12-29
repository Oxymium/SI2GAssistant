package com.oxymium.si2gassistant.domain.entities.mock

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.oxymium.si2gassistant.domain.entities.Person
import com.oxymium.si2gassistant.domain.entities.Suggestion

val FIRSTNAMES = listOf(
    "Olivia", "Liam", "Sophia", "Noah", "Emma", "Jackson", "Ava", "Lucas", "Isabella", "Aiden",
    "Mia", "Elijah", "Harper", "Alexander", "Evelyn", "James", "Abigail", "Benjamin", "Scarlett", "Logan",
    "Grace", "Oliver", "Chloe", "Lucas", "Lily", "William", "Zoe", "Ethan", "Sophia", "Mason",
    "Amelia", "Henry", "Ella", "Samuel", "Emily", "Wyatt", "Layla", "Caleb", "Aria", "Jack",
    "Stella", "Leo", "Maya", "Daniel", "Victoria", "Owen", "Aurora", "Gabriel", "Penelope", "Isaac"
)

val LASTNAMES = listOf(
    "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
    "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
    "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King",
    "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Nelson", "Carter", "Mitchell",
    "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart"
)

fun generateRandomValidatedModules(): String? {
    val maxModule = 8
    // Generate a random subset of modules
    val randomModules = (1..maxModule).shuffled().take((0..maxModule).random())
    return if (randomModules.isNotEmpty()) {
        randomModules.joinToString(", ")
    } else {
        null
    }

}
fun provideRandomPerson(): Person {

    return Person(
        null,
        "",
        FIRSTNAMES.random(),
        LASTNAMES.random(),
        generateRandomValidatedModules(),
        ALL_ACADEMIES.random().shortTitle,
        ""
    )

}