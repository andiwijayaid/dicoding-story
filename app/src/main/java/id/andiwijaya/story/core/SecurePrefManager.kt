package id.andiwijaya.story.core

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import id.andiwijaya.story.core.Constants.Preference.SECRET_PREFERENCE

class SecurePrefManager(app: Application) {

    private val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        SECRET_PREFERENCE,
        masterKeys,
        app,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun setString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? = sharedPreferences.getString(key, null)

    @Synchronized
    fun removeString(key: String) = sharedPreferences.edit().remove(key).commit()

}