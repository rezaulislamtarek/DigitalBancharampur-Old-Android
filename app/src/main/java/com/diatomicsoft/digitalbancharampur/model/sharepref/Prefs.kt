package com.diatomicsoft.digitalbancharampur.model.sharepref

import android.content.Context
import android.content.SharedPreferences
import com.diatomicsoft.digitalbancharampur.model.data.Auth

open class Prefs(context: Context) {
    private val contributorId = "contributorId"
    private val name = "name"
    private val token = "token"
    private val authKey = "authKey"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("digitalSharedPref", Context.MODE_PRIVATE)

    fun saveAuth(auth: Auth) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(contributorId, auth.id)
        editor.putString(token, auth.token)
        editor.putBoolean(authKey, auth.auth)
        editor.apply()
        editor.commit()
    }

    fun getContributorId(): Int {
        return sharedPreferences.getInt(contributorId, 0)
    }

    fun getAuthToken(): String {
        return sharedPreferences.getString(token, "defaultToken")!!
    }
    fun getAuth():Boolean{
        return sharedPreferences.getBoolean(authKey,false)
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}