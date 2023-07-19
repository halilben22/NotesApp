package com.example.todo.repository

import com.example.todo.Model.NoteDao
import com.example.todo.Model.NoteData
import javax.inject.Inject


class NoteRepo @Inject constructor(private val dao: NoteDao) {



   val readAllNotes:List<NoteData>
      get() {
       return  dao.getAll()
      }

   suspend fun addNotes(noteData: NoteData){
      dao.insert(noteData)
   }

   suspend fun deleteNotes(noteData: NoteData){
      dao.delete(noteData)
   }

   suspend fun updateNotes(note:String,id:Int){
      dao.update(note,id)
   }

   fun getAllNotes():List<NoteData>{
     return dao.getAll()
   }
}