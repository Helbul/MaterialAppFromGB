package com.example.materialappfromgb.view.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.materialappfromgb.R
import com.example.materialappfromgb.model.NoteData
import com.example.materialappfromgb.model.NoteData.Companion.TYPE_NOTE
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<NoteData, Boolean>>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> NoteViewHolder(
                inflater.inflate(R.layout.item_note, parent, false) as View
            )
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.notes_item_header, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].first.type
    }

    fun appendItem(noteData: NoteData) {
        data.add(Pair(noteData, false))
        notifyItemInserted(itemCount - 1)
    }

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    private val currentDate = GregorianCalendar()
    private fun generateItem() = Pair(NoteData(TYPE_NOTE,"Заметка", "описание", currentDate, "текст"), false)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class NoteViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<NoteData, Boolean>) {

            itemView.findViewById<TextView>(R.id.name).text = data.first.name
            itemView.findViewById<TextView>(R.id.description).text = data.first.description
            itemView.findViewById<TextView>(R.id.content).text = data.first.content

            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateStr: String = dateFormat.format(data.first.date.time)
            itemView.findViewById<TextView>(R.id.date).text = dateStr


            itemView.setOnClickListener {
                toggleText()
            }

            itemView.findViewById<TextView>(R.id.content).visibility =
                if (data.second) View.VISIBLE else View.GONE

            itemView.findViewById<ImageView>(R.id.hamburger).setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }


        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun addItem(note: NoteData?) {
            note?.let { data.add(layoutPosition, Pair(note, false)) } ?: data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<NoteData, Boolean>) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
        }
    }
}