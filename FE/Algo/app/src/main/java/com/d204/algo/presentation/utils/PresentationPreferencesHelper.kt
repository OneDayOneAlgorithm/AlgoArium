package com.d204.algo.presentation.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject

open class PresentationPreferencesHelper @Inject constructor(context: Context) {
    companion object {
        private const val PREF_PACKAGE_NAME = "com.d204.algo.presentation.preferences" // string
        private const val PREF_KEY_USER_EMAIL = "user_email" // String // email
        private const val PREF_KEY_USER_ID = "user_id" // Long
        private const val PREF_KEY_USER_PROFILE = "user_profile" // string
        private const val PREF_KEY_USER_NICKNAME = "user_nickname" // string
        private const val PREF_KEY_USER_TIER = "user_tier" // int
        private const val PREF_KEY_USER_PRE_TIER = "user_tier_pre"
        private const val PREF_KEY_ACCESS = "access_token" // string
        private const val PREF_KEY_REFRESH = "refresh_token" // string
        private const val PREF_KEY_SOLVED_AC_ID = "solved_ac_id" // string
    }

    private fun newMasterKeyAlias(context: Context): MasterKey =
        MasterKey
            .Builder(context.applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val preferences =
        EncryptedSharedPreferences.create(
            context,
            PREF_PACKAGE_NAME,
            newMasterKeyAlias(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // AES256_SIV으로 key를 암호화
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM, // AES256_GCM으로 value를 암호화
        )

    // 저장할 내용
    var prefUserId: Long
        get() = preferences.getLong(PREF_KEY_USER_ID, 0)
        set(userId) = preferences.edit().putLong(PREF_KEY_USER_ID, userId).apply()

    // 저장할 내용
    var prefUserEmail: String?
        get() = preferences.getString(PREF_KEY_USER_EMAIL, "")
        set(email) = preferences.edit().putString(PREF_KEY_USER_EMAIL, email).apply()

    // 저장할 내용
    var prefUserProfile: String?
        get() = preferences.getString(PREF_KEY_USER_PROFILE, "")
        set(profile) = preferences.edit().putString(PREF_KEY_USER_PROFILE, profile).apply()

    // 저장할 내용
    var prefUserNickname: String?
        get() = preferences.getString(PREF_KEY_USER_NICKNAME, "")
        set(nickname) = preferences.edit().putString(PREF_KEY_USER_NICKNAME, nickname).apply()

    // 저장할 내용
    var prefUserTier: Int
        get() = preferences.getInt(PREF_KEY_USER_TIER, -1)
        set(tier) = preferences.edit().putInt(PREF_KEY_USER_TIER, tier).apply()

    // 저장할 내용
    var prefUserPreTier: Int
        get() = preferences.getInt(PREF_KEY_USER_PRE_TIER, -1)
        set(pretier) = preferences.edit().putInt(PREF_KEY_USER_PRE_TIER, pretier).apply()

    // 저장할 내용
    var prefAccessToken: String?
        get() = preferences.getString(PREF_KEY_ACCESS, "")
        set(accessToken) = preferences.edit().putString(PREF_KEY_ACCESS, accessToken).apply()

    // 저장할 내용
    var prefRefreshToken: String?
        get() = preferences.getString(PREF_KEY_REFRESH, "")
        set(refreshToken) = preferences.edit().putString(PREF_KEY_REFRESH, refreshToken).apply()

    var prefSolvedAcId: String?
        get() = preferences.getString(PREF_KEY_SOLVED_AC_ID, "")
        set(solvedAcId) = preferences.edit().putString(PREF_KEY_SOLVED_AC_ID, solvedAcId).apply()
}
