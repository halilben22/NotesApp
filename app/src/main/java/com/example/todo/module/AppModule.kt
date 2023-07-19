package com.example.todo.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todo.Model.NoteDao
import com.example.todo.RoomDB.NoteDatabase
import com.example.todo.constants.Constants
import com.example.todo.pref.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


   @Singleton
   @Provides
   fun getAppDB(context: Application): NoteDatabase {
      return NoteDatabase.getAppDB(context)
   }




   @Singleton
   @Provides
   fun getDao(appDB: NoteDatabase): NoteDao {
      return appDB.noteDao()
   }




   @Provides
   @Singleton

   fun provideSharedPreferences(@ApplicationContext context: Context) =
      context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)


   @Provides
   @Singleton
   fun provideSessionManager(preferences: SharedPreferences) = SessionManager(preferences)

}