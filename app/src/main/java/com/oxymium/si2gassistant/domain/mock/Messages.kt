package com.oxymium.si2gassistant.domain.mock

import com.oxymium.si2gassistant.domain.entities.Message
import java.util.Calendar

const val startDate = 1698793200000 // 01/11/2023
val mails = listOf(
    "super@gmail.test",
    "normal@gmail.test"
)
val chatSentences = listOf(
    "Greetings! I trust this message finds you well. I wanted to discuss the recent developments in our project and gather your insights.",
    "Thank you for your prompt response. I appreciate your attention to detail, and I'm confident that together we can address any challenges that may arise.",
    "Per our earlier conversation, I would like to propose a meeting next week to finalize the project timeline and allocate resources accordingly. Your availability for this discussion would be highly valuable.",
    "I have reviewed the proposal you submitted, and I must commend your team for their thoroughness. However, I would appreciate some clarification on a few key points before moving forward.",
    "In light of our upcoming deadline, I wanted to ensure that all team members are aligned on the project goals and are clear about their respective responsibilities. Could we schedule a brief team meeting to address any potential concerns?",
    "I wanted to bring to your attention an opportunity for collaboration that has recently emerged. I believe it aligns well with our strategic objectives, and I would like to discuss how we can leverage this to our advantage.",
    "I understand that your schedule is quite busy, but I was hoping to arrange a brief phone call to discuss a pressing matter. Please let me know a time that works for you, and I will ensure to keep the conversation efficient and focused.",
    "Following our recent discussion on cost optimization, I have conducted a thorough analysis and identified several areas where we can streamline expenses without compromising the quality of our deliverables. I would like to discuss these findings with you at your earliest convenience.",
    "Your expertise in this matter is highly regarded, and I would appreciate your guidance on the best approach to address the current challenges we are facing. A short meeting to discuss potential solutions would be invaluable.",
    "I wanted to express my gratitude for your team's exceptional performance during the last quarter. The dedication and hard work have not gone unnoticed, and I am confident that with continued collaboration, we will achieve even greater success in the future."
)
fun provideRandomMessage(): Message {
    val nowInMillis = Calendar.getInstance().timeInMillis
    return Message(
        "",
        (startDate..nowInMillis).random(),
        mails.random(),
        chatSentences.random()
        )
}
