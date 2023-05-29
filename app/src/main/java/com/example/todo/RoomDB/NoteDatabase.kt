package com.example.todo.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.Model.NoteDao
import com.example.todo.Model.NoteData

@Database(entities = arrayOf(NoteData::class), version = 1)
abstract class NoteDatabase: RoomDatabase() {
   abstract fun noteDao():NoteDao
}