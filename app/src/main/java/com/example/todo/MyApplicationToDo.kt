package com.example.todo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplicationToDo:Application() {

   override fun onCreate() {
      super.onCreate()
   }
}