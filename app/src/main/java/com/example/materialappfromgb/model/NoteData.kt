package com.example.materialappfromgb.model

import java.util.*

data class NoteData(
    val type: Int = TYPE_NOTE,
    val name: String = "",
    val description: String = "",
    val date: Calendar,
    val content: String =""
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NOTE = 1
    }
}
