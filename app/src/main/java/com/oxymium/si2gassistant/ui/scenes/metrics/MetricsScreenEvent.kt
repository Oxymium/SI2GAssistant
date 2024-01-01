package com.oxymium.si2gassistant.ui.scenes.metrics

sealed interface MetricsScreenEvent {
    data object OnOverallMetricsButtonClick: MetricsScreenEvent

    data object OnBugTicketsButtonClick: MetricsScreenEvent
}