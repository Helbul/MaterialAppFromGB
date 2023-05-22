package com.example.materialappfromgb.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.materialappfromgb.R
import com.example.materialappfromgb.model.NoteData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AddNoteDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<EditText>(R.id.input_name)
        val description = view.findViewById<EditText>(R.id.input_description)
        val content = view.findViewById<EditText>(R.id.input_content)
        val date = view.findViewById<TextView>(R.id.input_date)
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val currentDate = GregorianCalendar()
        date.text = dateFormat.format(currentDate.time)

        val saveButton = view.findViewById<Button>(R.id.add_note)
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .build()
        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = Date(selection!!)
            date.text = dateFormat.format(selectedDate)
        }

        date.setOnClickListener { datePicker.show(parentFragmentManager, "MaterialDatePicker") }
        view.findViewById<View>(R.id.add_note).setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                saveButton.isEnabled = false
                val note = NoteData(
                    TYPE_NOTE,
                    name.text.toString(),
                    description.text.toString(),
                    currentDate,
                    content.text.toString()
                )
                    addNote(note)
            }


            private fun addNote(note: NoteData) {
                val bundle = Bundle()
                bundle.putString(ARG_NOTE_NAME, note.name)
                bundle.putString(ARG_NOTE_DESCRIPTION, note.description)
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val dateStr = dateFormat.format(note.date.time)
                bundle.putString(ARG_NOTE_DATE, dateStr)
                bundle.putString(ARG_NOTE_CONTENT, note.content)
                parentFragmentManager.setFragmentResult(KEY_RESULT_ADD_NOTE, bundle)
                dismiss()
            }
        })
    }

    companion object {
        const val TYPE_NOTE = 1
        const val KEY_RESULT_ADD_NOTE = "KEY_RESULT_ADD_NOTE"
        const val ARG_NOTE_NAME = "ARG_NOTE_NAME"
        const val ARG_NOTE_DESCRIPTION = "ARG_NOTE_DESCRIPTION"
        const val ARG_NOTE_CONTENT = "ARG_NOTE_CONTENT"
        const val ARG_NOTE_DATE = "ARG_NOTE_DATE"

        fun addInstance(): AddNoteDialogFragment {
            return AddNoteDialogFragment()
        }
    }
}