package com.redfin.redfin.support

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class PrivateInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        val response = chain.proceed(requestBuilder.build())
        return response
    }
}