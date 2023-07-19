package com.example.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todo.adapter.NoteAdapter
import com.example.todo.databinding.ActivityNoteListBinding
import com.example.todo.viewmodel.NotesScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class NoteListActivity : AppCompatActivity() {

   private lateinit var adapter: NoteAdapter
   private lateinit var binding: ActivityNoteListBinding
   private val noteViewModel by lazy {
      ViewModelProvider(this, defaultViewModelProviderFactory)[NotesScreenViewModel::class.java]
   }


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityNoteListBinding.inflate(layoutInflater)
      val view = binding.root
      setContentView(view)
      noteViewModel.loadNoteDatas()


      noteViewModel.getNotesObserver().observe(this) {
         createAdapter()
         adapter.setList(it)

      }

   }


   private fun createAdapter() {
      binding.recycleView.layoutManager = GridLayoutManager(this, 2)
      adapter = NoteAdapter(noteViewModel, this)
      binding.recycleView.adapter = adapter
   }

}