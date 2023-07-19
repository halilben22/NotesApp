package com.example.todo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.Model.NoteData
import com.example.todo.repository.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotesScreenViewModel @Inject constructor(private val repo: NoteRepo) :ViewModel() {

   var allNotes: MutableLiveData<List<NoteData>> =MutableLiveData()


   fun getNotesObserver(): MutableLiveData<List<NoteData>> {
      return allNotes
   }


  suspend fun addFav(noteData:NoteData){
     repo.addNotes(noteData)

   }

   suspend fun deleteFav(noteData:NoteData){
      repo.deleteNotes(noteData)

   }


   suspend fun update(note:String,id:Int){
      repo.updateNotes(note,id)

   }

   fun loadNoteDatas(){
      val refreshedList=repo.readAllNotes
      allNotes.postValue(refreshedList)
   }

}