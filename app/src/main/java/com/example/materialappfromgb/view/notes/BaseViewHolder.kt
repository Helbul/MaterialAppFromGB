package com.example.materialappfromgb.view.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.materialappfromgb.model.NoteData

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<NoteData, Boolean>)
}