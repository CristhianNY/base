package com.redfin.redfin.support

import kotlinx.coroutines.withContext

import retrofit2.Response

abstract class BaseDataSource {

    var dispatcherProvider: DispatcherProvider = DispatcherProviderImpl()

    protected suspend fun <T> executeNetworkAction(action: suspend () -> T): T =
        withContext(dispatcherProvider.network) { action() }

    protected inline fun <reified T> getResult(call: () -> Response<T>): Result<T> {
        var code = 0

        try {
            val response = call()
            code = response.code()

            if (response.isSuccessful) {
                return Result.success(response.body())
            }
            return error(
                Exception(response.message()), code, response.errorBody()?.string().orEmpty(),
                response.body()
            )
        } catch (e: Exception) {
            return error(e, code, "")
        }
    }

    fun <T> error(e: Exception, code: Int, errorBody: String, data: T? = null): Result<T> {
        return Result.error(e, code, errorBody, data)
    }
}