package com.redfin.redfin.support

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher

    val network: CoroutineDispatcher
}
