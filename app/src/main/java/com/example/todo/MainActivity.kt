package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.todo.Model.NoteData
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.viewmodel.NotesScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var noteList: List<NoteData>


   val viewModel by lazy {


      ViewModelProvider(this, defaultViewModelProviderFactory)[NotesScreenViewModel::class.java]
   }

   @SuppressLint("SuspiciousIndentation")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      val view = binding.root
      setContentView(view)
      if (binding.noteView.text.isEmpty()) {
         binding.showListButton.visibility = View.GONE
      }
checkVis()



      binding.showListButton.setOnClickListener {
         val intent = Intent(this, NoteListActivity::class.java)
         binding.noteView.text.clear()
         startActivity(intent)


      }

      viewModel.getNotesObserver().observe(this) {
         noteList = it
         viewModel.loadNoteDatas()

      }


      binding.saveButton.setOnClickListener {
         val note = (binding.noteView.text)
         CoroutineScope(Dispatchers.IO).launch {
            viewModel.addFav(NoteData(note.toString(), false))
            withContext(Dispatchers.Main) {
               changeButtonVis()
               Toast.makeText(applicationContext, "Note saved to the list!", Toast.LENGTH_SHORT)
                  .show()

            }
         }
      }

   }

   private fun changeButtonVis() {
      binding.showListButton.visibility = View.VISIBLE


   }

 private fun checkVis() {

      CoroutineScope(Dispatchers.IO).launch {
         noteList = viewModel.allNotes.value!!

         withContext(Dispatchers.Main) {
            if (noteList.isEmpty()) {

               binding.showListButton.visibility = View.GONE
            } else {
               changeButtonVis()
            }
         }
}


   }


}




