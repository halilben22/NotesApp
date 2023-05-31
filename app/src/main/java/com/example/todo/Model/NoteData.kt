package com.example.todo.Model

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("todo_list")
data class NoteData(

   @ColumnInfo("note")
   val note:String?

):java.io.Serializable{

   @PrimaryKey(autoGenerate = true)
   var id:Int?=null
}
