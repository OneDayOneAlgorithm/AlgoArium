package com.d204.algo.remote.api

import com.d204.algo.presentation.utils.AuthAuthenticator
import com.d204.algo.presentation.utils.AuthInterceptor
import com.d204.algo.presentation.utils.TokenManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

// RemoteModule에서 사용합니다.
object ServiceFactory {
    // 각 서비스 별로 create 함수를 별도로 만든다.
    fun createUserService(isDebug: Boolean, baseUrl: String, tokenManager: TokenManager): UserService {
        val retrofit = createRetrofit(isDebug, baseUrl, tokenManager)
        return retrofit.create(UserService::class.java)
    }

    fun createRankingService(isDebug: Boolean, baseUrl: String, tokenManager: TokenManager): RankingService {
        val retrofit = createRetrofit(isDebug, baseUrl, tokenManager)
        return retrofit.create(RankingService::class.java)
    }

    fun createStatusService(isDebug: Boolean, baseUrl: String, tokenManager: TokenManager): StatusService {
        val retrofit = createRetrofit(isDebug, baseUrl, tokenManager)
        return retrofit.create(StatusService::class.java)
    }

    fun createProblemService(isDebug: Boolean, baseUrl: String, tokenManager: TokenManager): ProblemService {
        val retrofit = createRetrofit(isDebug, baseUrl, tokenManager)
        return retrofit.create(ProblemService::class.java)
    }

    private val moshi = Moshi.Builder() // adapter
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun createRetrofit(isDebug: Boolean, baseUrl: String, tokenManager: TokenManager): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient(createLoggingInterceptor(isDebug), tokenManager))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, tokenManager: TokenManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
//            .authenticator(createAuthAuthenticator(tokenManager))
//            .addInterceptor(createAuthInterceptor(tokenManager))
            .build()
    }

    private fun createLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (isDebug) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun createAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    private fun createAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator {
        return AuthAuthenticator(tokenManager)
    }

    private const val OK_HTTP_TIMEOUT = 10L
}
