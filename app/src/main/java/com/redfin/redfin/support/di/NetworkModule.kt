package com.redfin.redfin.support.di
import com.redfin.redfin.BuildConfig
import com.redfin.redfin.support.PrivateInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val CONTENT_TYPE_KEY = "Content-Type"
private const val CONTENT_TYPE = "application/json"
private const val HEADER_ACCEPT_KEY = "Accept"
private const val HEADER_ACCEPT = "application/json"
private const val TIMEOUT = 90L
const val REMOTE_API = "remoteApi"
const val BASE_PATH_URL = "baseUrl"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named(BASE_PATH_URL)
    fun provideRetrofit(@Named(REMOTE_API) httpClient: OkHttpClient): Retrofit {
        val baseUrl = BuildConfig.BASE_URL
        require(baseUrl.isNotEmpty() && (baseUrl.startsWith("http://") || baseUrl.startsWith("https://"))) {
            "Invalid BASE_URL: $baseUrl"
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }


    @Provides
    @Singleton
    @Named(REMOTE_API)
    fun provideOkHttpClient(
        privateInterceptor: PrivateInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(privateInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                requestBuilder.header(CONTENT_TYPE_KEY, CONTENT_TYPE)
                requestBuilder.header(HEADER_ACCEPT_KEY, HEADER_ACCEPT)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }
}