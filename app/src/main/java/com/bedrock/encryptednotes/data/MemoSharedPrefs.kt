package com.bedrock.encryptednotes.data

import android.content.Context

object MemoSharedPrefs {
    private const val PREFS_NAME = "memo_preferences"
    private const val IS_FIRST_START = "is_first_start"
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
}

