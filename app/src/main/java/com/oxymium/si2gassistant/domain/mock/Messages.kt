package com.oxymium.si2gassistant.domain.mock

import com.oxymium.si2gassistant.domain.entities.Message

fun provideRandomMessage(): Message {
    return Message(
        "",
        0L,
        "",
        ""
        )
}
