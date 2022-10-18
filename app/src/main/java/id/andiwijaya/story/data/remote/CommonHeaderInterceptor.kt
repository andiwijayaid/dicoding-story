package id.andiwijaya.story.data.remote

import id.andiwijaya.story.core.Constants.Network.AUTHORIZATION
import id.andiwijaya.story.core.Constants.Network.BEARER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CommonHeaderInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .appendAuthorization()
        return chain.proceed(request.build())
    }

    // temp value
    private fun Request.Builder.appendAuthorization(): Request.Builder = this.addHeader(
        AUTHORIZATION,
        "$BEARER eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLV9mUHIyX2IyZVpOMnhIZjAiLCJpYXQiOjE2NjQ0NTQzNjJ9.AJtLqVUXJ9LnOoQUrqnz-1mPBl087p7Kyb25aB5Yih0"
    )
}