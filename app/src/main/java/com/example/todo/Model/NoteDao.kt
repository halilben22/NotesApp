package com.example.todo.Model

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface NoteDao {

   @Query("SELECT * FROM todo_list")
fun getAll():List<NoteData>

   @Insert
  suspend fun insert(place: NoteData)

   @Delete
  suspend fun delete(place: NoteData)

   @Query("UPDATE todo_list SET note=:note WHERE id=:id")
  suspend fun update(note:String,id:Int)


  /* @Query("UPDATE todo_list SET isDone=:isDone WHERE id=:id")
   fun updateDoneStatus(isDone:Boolean,id:Int): Completable
*/
}