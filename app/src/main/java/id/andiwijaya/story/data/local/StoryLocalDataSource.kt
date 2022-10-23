package id.andiwijaya.story.data.local

import id.andiwijaya.story.core.Constants.Preference.PREF_KEY_TOKEN
import id.andiwijaya.story.core.SecurePrefManager
import javax.inject.Inject

class StoryLocalDataSource @Inject constructor(
    private val securePrefManager: SecurePrefManager
) {

    fun saveToken(string: String?) = string?.let { securePrefManager.setString(PREF_KEY_TOKEN, it) }

    fun loadToken(): String = securePrefManager.getString(PREF_KEY_TOKEN).orEmpty()

    fun removeToken() = securePrefManager.removeString(PREF_KEY_TOKEN)

}