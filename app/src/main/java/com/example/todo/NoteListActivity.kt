package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todo.Adapter.NoteAdapter
import com.example.todo.Model.NoteDao
import com.example.todo.Model.NoteData
import com.example.todo.RoomDB.NoteDatabase
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.ActivityNoteListBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NoteListActivity : AppCompatActivity() {

   private lateinit var noteDB: NoteDao
   private lateinit var adapter: NoteAdapter
   private lateinit var binding: ActivityNoteListBinding
   private  var disposable= CompositeDisposable()
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding =  ActivityNoteListBinding.inflate(layoutInflater)
      val view = binding.root
      setContentView(view)

      val db= Room.databaseBuilder(applicationContext,
         NoteDatabase::class.java,"Notes").build()
      noteDB=db.noteDao()
      getAllNotes()
   }
   private fun handleResponse(noteList: List<NoteData>){
      binding.recycleView.layoutManager=
         GridLayoutManager(this,2)
      adapter= NoteAdapter(noteList,this,noteDB,disposable)
      binding.recycleView.adapter=adapter

   }
   private fun getAllNotes(){

        disposable.add(noteDB.getAll().
 observeOn(AndroidSchedulers.mainThread())
           .subscribeOn(Schedulers.io()).
           subscribe(this::handleResponse))
   }

   override fun onDestroy() {
      super.onDestroy()

   disposable.dispose()
   }
}