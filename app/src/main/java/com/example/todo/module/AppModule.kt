package com.example.todo.module

import android.app.Application
import com.example.todo.Model.NoteDao
import com.example.todo.RoomDB.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}