package com.example.todo.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Model.NoteData
import com.example.todo.R
import com.example.todo.viewmodel.NotesScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NoteAdapter @Inject constructor(
   private val viewModelNote: NotesScreenViewModel,
   private val context: Context
)

   :
   RecyclerView.Adapter<NoteAdapter.HolderClass>() {
   class HolderClass(val view: View) :
      RecyclerView.ViewHolder(view) {

   }

   private var noteList: List<NoteData>? = null

   @SuppressLint("NotifyDataSetChanged")
   fun setList(noteList: List<NoteData>) {

      this.noteList = noteList
      notifyDataSetChanged()
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): HolderClass {
      val inflater =
         LayoutInflater.from(parent.context)
      val view = inflater.inflate(
         R.layout.list_items_recycler,
         parent,
         false
      )
      return HolderClass(view)
   }

   override fun onBindViewHolder(
      holder: HolderClass,
      position: Int
   ) {


      val noteTextRec =
         holder.view.findViewById<TextView>(R.id.noteTextRec)
      noteTextRec.text =
         noteList!![position].note

      val cardRecycler =
         holder.view.findViewById<CardView>(R.id.cardRecycler)

      val checked = holder.view.findViewById<TextView>(R.id.checked)


      noteList!![position].isDone = false


      /*cardRecycler.setOnLongClickListener {
if ( noteList[position].isDone==true) {

   println("Buraya girdi")
   noteTextRec.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

 disposable.add(noteDB.updateDoneStatus(noteList[position].isDone!!,noteList[position].id!!).
 observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io()).
    subscribe())
   checked.visibility=View.VISIBLE

   noteList[position].isDone=false

}
        else{

           println("Ötekine girdi")
   noteTextRec.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG.inv()


   disposable.add(noteDB.updateDoneStatus(noteList[position].isDone!!,noteList[position].id!!).
   observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io()).
      subscribe())

   noteList[position].isDone=true


   checked.visibility=View.GONE
         }

       true
       }*/


      fun showDialog(position: Int) {

         val builder = Dialog(context)
         val inflater = LayoutInflater.from(context)
         val dialogLayout = inflater.inflate(R.layout.custom_dialog, null)
         val editText = dialogLayout.findViewById<EditText>(R.id.editTextPopUP)
         val updateButton = dialogLayout.findViewById<Button>(R.id.updateButton)
         val deleteButton = dialogLayout.findViewById<Button>(R.id.deleteButton)


         with(builder) {
            editText.setText(noteList!![position].note)
            updateButton.setOnClickListener {
               CoroutineScope(Dispatchers.IO).launch {
                  viewModelNote.update(editText.text.toString(), noteList!![position].id!!)
                  cancel()
                  viewModelNote.loadNoteDatas()

                  showToast("Note successfully updated")
               }

            }

            deleteButton.setOnClickListener {
               CoroutineScope(Dispatchers.IO).launch {
                  viewModelNote.deleteFav(noteList!![position])
                   viewModelNote.loadNoteDatas()
                  withContext(Dispatchers.Main) {
                     editText.setText("")
                     cancel()
                     showToast("Note deleted successfully")

                  }
               }

            }

            setContentView(dialogLayout)
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
         }


      }
      cardRecycler.setOnClickListener {
         showDialog(position)

      }

   }

   override fun getItemCount(): Int {
      return noteList?.size ?: 4
   }


   private fun showToast(message: String) {
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
   }
}











