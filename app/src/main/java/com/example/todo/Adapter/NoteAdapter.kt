package com.example.todo.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Model.NoteDao
import com.example.todo.Model.NoteData
import com.example.todo.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class NoteAdapter(
   val noteList: List<NoteData>,
   val context: Context,
  val noteDB:NoteDao,
  val disposable: CompositeDisposable
) :
   RecyclerView.Adapter<NoteAdapter.HolderClass>() {
   class HolderClass(val view: View) :
      RecyclerView.ViewHolder(view) {

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
         noteList[position].note.toString()

      val cardRecycler =
         holder.view.findViewById<CardView>(R.id.cardRecycler)

      val checked=holder.view.findViewById<TextView>(R.id.checked)


      noteList[position].isDone=false

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
       }
      cardRecycler.setOnClickListener {
         showDialog(position)

      }*/

   }

   override fun getItemCount(): Int {
      return noteList.size
   }

   private fun showDialog(position: Int) {

    val builder=Dialog(context)
      val inflater=LayoutInflater.from(context)
      val dialogLayout = inflater.inflate(R.layout.custom_dialog,null)
       val editText=dialogLayout.findViewById<EditText>(R.id.editTextPopUP)
      val updateButton=dialogLayout.findViewById<Button>(R.id.updateButton)
      val deleteButton=dialogLayout.findViewById<Button>(R.id.deleteButton)



with(builder){
   editText.setText(noteList[position].note)
updateButton.setOnClickListener {
   noteList[position].id?.let { it1 ->
      noteDB.update(editText.text.toString(),
         it1
      )
   }?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(
      Schedulers.io())?.let { it2 ->
      disposable.add(
      it2.subscribe())
   }

   showToast("Note updated successfully!")
}
   deleteButton.setOnClickListener {
      disposable.add(noteDB.delete(noteList[position])
         .observeOn(AndroidSchedulers.mainThread()).
         subscribeOn(Schedulers.io())
         .subscribe())
      editText.setText("")
      builder.dismiss()
      showToast("Note deleted successfully!")
   }
   setContentView(dialogLayout)
   builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
   show()

}
   }


private fun showToast(message: String){
   Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
}

}