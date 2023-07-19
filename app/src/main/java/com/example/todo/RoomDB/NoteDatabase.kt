package com.example.todo.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.Model.NoteDao
import com.example.todo.Model.NoteData

@Database(entities = [NoteData::class], version = 2)
abstract class NoteDatabase: RoomDatabase() {
   abstract fun noteDao():NoteDao


   companion object {

      private var dbInstance: NoteDatabase? = null
      fun getAppDB(context: Context): NoteDatabase{
         if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(
               context.applicationContext,
               NoteDatabase::class.java,
               "notes_database"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
         }

         return dbInstance!!
      }

   }

}