package com.example.materialappfromgb.view.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.materialappfromgb.databinding.FragmentNoteListBinding
import com.example.materialappfromgb.model.NoteData
import com.example.materialappfromgb.model.NoteData.Companion.TYPE_HEADER
import com.example.materialappfromgb.model.NoteData.Companion.TYPE_NOTE
import com.example.materialappfromgb.viewmodel.NoteListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class NoteListFragment : Fragment() {

    companion object {
        fun newInstance() = NoteListFragment()

        const val ARG_NOTE_NAME = "ARG_NOTE_NAME"
        const val ARG_NOTE_DESCRIPTION = "ARG_NOTE_DESCRIPTION"
        const val ARG_NOTE_CONTENT = "ARG_NOTE_CONTENT"
        const val ARG_NOTE_DATE = "ARG_NOTE_DATE"
    }

    private var _binding: FragmentNoteListBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: NoteListViewModel

    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val curDate = GregorianCalendar()
        val data = arrayListOf(
            Pair(NoteData(TYPE_HEADER, date = curDate), false)
        )

        val adapter = NotesAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(noteData: NoteData) {
                    Toast.makeText(activity, noteData.name, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        binding.recyclerView.adapter = adapter

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.add.setOnClickListener {
//            adapter.appendItem()
            AddNoteDialogFragment
                .addInstance()
                .show(parentFragmentManager, "AddNoteDialogFragment")
        }

        parentFragmentManager
            .setFragmentResultListener(
                AddNoteDialogFragment.KEY_RESULT_ADD_NOTE,
                viewLifecycleOwner
            ) { requestKey, result ->

                val nameArg = result.getString(ARG_NOTE_NAME) ?: ""
                val descriptionArg = result.getString(ARG_NOTE_DESCRIPTION) ?: ""
                val contentArg = result.getString(ARG_NOTE_CONTENT) ?: ""
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val dateArg = Calendar.getInstance()
                dateArg.time = result.getString(ARG_NOTE_DATE)?.let { dateFormat.parse(it) }

                val note: NoteData =
                    NoteData(TYPE_NOTE, nameArg, descriptionArg, dateArg, contentArg)

                adapter.appendItem(note)
 //               val index: Int = adapter.addNote(note)
//                notesAdapter.notifyItemInserted(index)
//                recyclerView.smoothScrollToPosition(index)
            }
    }
}