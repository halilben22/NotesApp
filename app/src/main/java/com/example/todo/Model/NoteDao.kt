package com.example.todo.Model

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable


@Dao
interface NoteDao {

   @Query("SELECT * FROM todo_list")
   fun getAll(): Flowable<List<NoteData>>

   @Insert
   fun insert(place: NoteData): Completable

   @Delete
   fun delete(place: NoteData): Completable

   @Query("UPDATE todo_list SET note=:note WHERE id=:id")
   fun update(note:String,id:Int): Completable


  /* @Query("UPDATE todo_list SET isDone=:isDone WHERE id=:id")
   fun updateDoneStatus(isDone:Boolean,id:Int): Completable
*/
}