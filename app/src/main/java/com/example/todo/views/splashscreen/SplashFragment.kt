package com.example.todo.views.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.databinding.FragmentSplashBinding
import com.example.todo.pref.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment() {

   private var _binding: FragmentSplashBinding? = null

   @Inject
   lateinit var sessionManager: SessionManager
   private val binding get() = _binding!!
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {

      _binding = FragmentSplashBinding.inflate(inflater, container, false)
      val view = binding.root


      Handler(Looper.getMainLooper()).postDelayed({


         findNavController().navigate(R.id.action_splashFragment2_to_homeFragment)
         val fragmentManager = FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
         fragmentManager.rem(R.id.splashFragment2)


      }, 2000)
      return view
   }

   override fun onDestroy() {
      super.onDestroy()

   }


}