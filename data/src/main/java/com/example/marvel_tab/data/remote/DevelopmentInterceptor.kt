package com.example.marvel_tab.data.remote

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevelopmentInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()

        val responseString = getResponseString(url)

        return if (responseString == null) {
            chain.proceed(chain.request())
        } else {
            getMockResponse(chain, responseString)
        }
    }

    private fun getMockResponse(chain: Interceptor.Chain, responseString: String): Response {
        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(
                responseString.toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getFailMockResponse(chain: Interceptor.Chain, responseString: String): Response {
        return Response.Builder()
            .code(412)
            .message("Fail")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(
                responseString.toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getResponseString(url: String): String? {
        val fileName = when {
            else -> null
        }
        return fileName?.let { loadJson(it) }
    }

    private fun loadJson(fileName: String): String? = try {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        null
    }
}
