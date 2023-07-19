package com.example.todo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todo.R
import com.example.todo.adapter.NoteAdapter
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.viewmodel.NotesScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {
   private var _binding: FragmentListBinding? = null

   private val binding get() = _binding!!
   private lateinit var adapter: NoteAdapter

   private val noteViewModel by lazy {
      ViewModelProvider(this, defaultViewModelProviderFactory)[NotesScreenViewModel::class.java]}

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
   }


      override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
      ): View {
         noteViewModel.loadNoteDatas()

         noteViewModel.getNotesObserver().observe(this) {
            createAdapter()
            adapter.setList(it)

         }


         _binding = FragmentListBinding.inflate(inflater, container, false)
         val view = binding.root


         binding.toolBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_homeFragment)
         }
         return view
      }
   private fun createAdapter() {
      binding.recycleView.layoutManager = GridLayoutManager(requireContext(), 2)
      adapter = NoteAdapter(noteViewModel, requireContext())
      binding.recycleView.adapter = adapter
   }

      override fun onDestroyView() {
         super.onDestroyView()
         _binding = null

      }

   }
