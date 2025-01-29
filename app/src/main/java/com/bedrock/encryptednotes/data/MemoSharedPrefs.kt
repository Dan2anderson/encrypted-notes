package com.bedrock.encryptednotes.data

import android.content.Context
import java.security.SecureRandom

object MemoSharedPrefs {
    private const val PREFS_NAME = "memo_preferences"
    private const val IS_FIRST_START = "is_first_start"
    private const val MY_KEY = "hash_key"
    fun saveIsFirstStart(context: Context, isFirstStart: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_FIRST_START, isFirstStart)
        editor.apply()
    }
    fun getIsFirstStart(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_FIRST_START, true)
    }
    fun saveKey(context: Context, key: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(MY_KEY, key)
        editor.apply()
    }
    fun getKey(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var key = sharedPreferences.getString(MY_KEY, "")
        if(key.isNullOrEmpty()){
            key = generateRandomString()
            saveKey(context, key)
        }
        return key
    }

    private fun generateRandomString(length: Int = 24): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = SecureRandom()
        val sb = StringBuilder(length)
        for (i in 0 until length) {
            sb.append(chars[random.nextInt(chars.length)])
        }
        return sb.toString()
    }
}

