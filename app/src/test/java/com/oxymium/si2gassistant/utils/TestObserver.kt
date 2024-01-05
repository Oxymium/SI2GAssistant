package com.oxymium.si2gassistant.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestObserver<T> @OptIn(ExperimentalCoroutinesApi::class) constructor(
    scope: CoroutineScope,
    testScheduler: TestCoroutineScheduler,
    flow: Flow<T>
) {
    val values = mutableListOf<T>()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val job = scope.launch(UnconfinedTestDispatcher(testScheduler)) {
        flow.collect { values.add(it) }
    }

    fun finish() {
        job.cancel()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.observe(scope: TestScope): TestObserver<T> {
    return TestObserver(scope, scope.testScheduler, this)
}
