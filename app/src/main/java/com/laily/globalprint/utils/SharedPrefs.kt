package com.laily.globalprint.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val _prefsFileName = "prefs"

    //TOKEN
    private val _authToken = "authTokenSave"

    //DATA USER DIMUAT KETIKA LOGIN
    private val _name = "nameAccount"
    private val _userID = "userID"
    private val _isAdmin = "isAdmin"
    private val _isStaff = "isStaff"
    private val _isCustomer = "isCustomer"

    private val prefs: SharedPreferences = context.getSharedPreferences(_prefsFileName, 0)

    var authTokenSave: String
        get() = prefs.getString(_authToken, "") ?: ""
        set(value) = prefs.edit().putString(_authToken, value).apply()

    var nameSave: String
        get() = prefs.getString(_name, "") ?: ""
        set(value) = prefs.edit().putString(_name, value).apply()

    var userIDSave: String
        get() = prefs.getString(_userID, "") ?: ""
        set(value) = prefs.edit().putString(_userID, value).apply()

    var isAdmin: Boolean
        get() = prefs.getBoolean(_isAdmin, false)
        set(value) = prefs.edit().putBoolean(_isAdmin, value).apply()

    var isStaff: Boolean
        get() = prefs.getBoolean(_isStaff, false)
        set(value) = prefs.edit().putBoolean(_isStaff, value).apply()

    var isCustomer: Boolean
        get() = prefs.getBoolean(_isCustomer, false)
        set(value) = prefs.edit().putBoolean(_isCustomer, value).apply()
}