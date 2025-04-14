package com.redfin.redfin.support

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface UseCase<in Params, out Type> where Type : Any {
    suspend operator fun invoke(
        params: Params,
        context: CoroutineContext = Dispatchers.IO
    ): ResultDomain<Type> =
        withContext(context) {
            invoke(params, context)
        }

    object None
}
