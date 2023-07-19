package com.example.todo.pref

import android.content.SharedPreferences
import com.example.todo.constants.Constants
import javax.inject.Inject

class SessionManager @Inject constructor(private val preferences: SharedPreferences) {





   fun getIsFirstRun() = preferences.getBoolean(Constants.FIRST_RUN_KEY, true)

   fun setIsFirstRun(value: Boolean) {
      val editor = preferences.edit()
      editor.putBoolean(Constants.FIRST_RUN_KEY, value)
      editor.apply()

   }
}