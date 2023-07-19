package com.example.todo.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todo.Model.NoteData
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.pref.SessionManager
import com.example.todo.viewmodel.NotesScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
   private var _binding: FragmentHomeBinding? = null
   private lateinit var noteList: List<NoteData>

   @Inject
   lateinit var sessionManager: SessionManager
   val viewModel by lazy {


      ViewModelProvider(this, defaultViewModelProviderFactory)[NotesScreenViewModel::class.java]
   }
   private val binding get() = _binding!!
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)


   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View{

      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      val view = binding.root
      if (sessionManager.getIsFirstRun()){
         sessionManager.setIsFirstRun(false)
      }

      checkVis()

      if (binding.noteView.text.isEmpty()) {
         binding.showListButton.visibility = View.GONE
      }

      binding.showListButton.setOnClickListener {
         findNavController().navigate(R.id.action_homeFragment_to_listFragment)
         binding.noteView.text.clear()



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
               Toast.makeText(requireContext(), "Note saved to the list!", Toast.LENGTH_SHORT)
                  .show()

            }
         }
      }




      return view
   }

   private fun changeButtonVis() {
      binding.showListButton.visibility = View.VISIBLE


   }
   private fun checkVis() {

      CoroutineScope(Dispatchers.IO).launch {
         noteList = viewModel.getAllNotes()



         withContext(Dispatchers.Main) {
            if (noteList.isEmpty()) {

               binding.showListButton.visibility = View.GONE
            } else {
               changeButtonVis()
            }
         }
      }


   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }
}