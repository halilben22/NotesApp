package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todo.Adapter.NoteAdapter
import com.example.todo.Model.NoteDao
import com.example.todo.Model.NoteData
import com.example.todo.RoomDB.NoteDatabase
import com.example.todo.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var noteList:ArrayList<NoteData>
   private var disposable= CompositeDisposable()
   private lateinit var noteDB:NoteDao
   @SuppressLint("SuspiciousIndentation")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding =  ActivityMainBinding.inflate(layoutInflater)
      val view = binding.root
      setContentView(view)
      if (binding.noteView.text.isEmpty()){
         binding.showListButton.visibility=View.GONE
      }

      val db= Room.databaseBuilder(applicationContext,NoteDatabase::class.java,"Notes").build()
      noteDB=db.noteDao()
checkVis()

      binding.showListButton.setOnClickListener {
    val intent=Intent(this,NoteListActivity::class.java)
         startActivity(intent)
disposable.clear()

         binding.noteView.text.clear()
      }


      binding.saveButton.setOnClickListener {
         val note=(binding.noteView.text)
         disposable.add(noteDB.insert(NoteData(note.toString())).
         observeOn(AndroidSchedulers.mainThread()).
         subscribeOn(Schedulers.io()).
         subscribe(this::changeButtonVis))
         Toast.makeText(this,"Note saved to the list!", Toast.LENGTH_SHORT).show()
      }

   }

   private fun changeButtonVis(){
      binding.showListButton.visibility=View.VISIBLE


   }

 private  fun checkVis(){
      disposable.add(noteDB.getAll().
      observeOn(AndroidSchedulers.mainThread())
         .subscribeOn(Schedulers.io()).
         subscribe(this::checkVisibility))

   }
   fun checkVisibility(noteList: List<NoteData>){
      if (noteList.isNotEmpty()){
         binding.showListButton.visibility=View.VISIBLE
      }
   }

   override fun onDestroy() {
      super.onDestroy()

      disposable.clear()
   }


}