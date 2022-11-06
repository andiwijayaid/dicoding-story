package id.andiwijaya.story.data.remote

import id.andiwijaya.story.core.Constants.Network.AUTHORIZATION
import id.andiwijaya.story.core.Constants.Network.BEARER
import id.andiwijaya.story.core.Constants.Preference.PREF_KEY_TOKEN
import id.andiwijaya.story.core.SecurePrefManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class CommonHeaderInterceptor @Inject constructor(
    private val securePrefManager: SecurePrefManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .appendAuthorization()
        return chain.proceed(request.build())
    }

    private fun Request.Builder.appendAuthorization(): Request.Builder = this.addHeader(
        AUTHORIZATION,
        "$BEARER ${securePrefManager.getString(PREF_KEY_TOKEN).orEmpty()}"
    )
}