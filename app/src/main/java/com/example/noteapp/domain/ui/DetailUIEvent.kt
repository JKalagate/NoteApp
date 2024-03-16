package com.example.noteapp.domain.ui

sealed class DetailUIEvent {

    data class BrushChange(val brushIndex: Int) : DetailUIEvent()
    data class TitleChange(val title: String) : DetailUIEvent()
    data class DescriptionChange(val description: String) : DetailUIEvent()


}