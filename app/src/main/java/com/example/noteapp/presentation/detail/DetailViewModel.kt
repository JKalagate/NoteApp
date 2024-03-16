package com.example.noteapp.presentation.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.AuthRepository
import com.example.noteapp.data.repository.StorageRepository
import com.example.noteapp.domain.ui.DetailUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: StorageRepository,
    private val auth : AuthRepository
) : ViewModel() {

    private val TAG = DetailViewModel::class.simpleName

    var inProgress by mutableStateOf(false)

    var noteUiState by mutableStateOf(DetailUIState())

    var noteList: Flow<List<DetailUIState>> = flowOf(emptyList())

    val user: String?
        get() = repository.user()

    fun onEvent(event: DetailUIEvent) {
        when (event) {

            is DetailUIEvent.BrushChange -> {
                noteUiState = noteUiState.copy(
                    brushIndex = event.brushIndex
                )
            }

            is DetailUIEvent.TitleChange -> {
                noteUiState = noteUiState.copy(
                    title = event.title
                )
            }

            is DetailUIEvent.DescriptionChange -> {
                noteUiState = noteUiState.copy(
                    description = event.description
                )
            }
        }
    }


    fun addNote(onComplete: () -> Unit) {
        try {
            inProgress = true
            user?.let { userId ->
                repository.addNote(
                    userId = userId,
                    brushIndex = noteUiState.brushIndex,
                    title = noteUiState.title,
                    description = noteUiState.description
                ) { isSuccessful ->
                    if (isSuccessful) {
                        onComplete.invoke()
                    }
                }
            } ?: Log.e(TAG, "User is null in addNote")
        } catch (e: Exception) {
            Log.d(TAG, "Error inside addNote NoteViewModel: $e")
        } finally {
            inProgress = false
        }
    }

    fun loadNotes() = viewModelScope.launch {
        try {
            user?.let { userId ->
                inProgress = true
                noteList = repository.getNotes(userId)
            } ?: Log.e(TAG, "User is null in loadNotes")
        } catch (e: Exception) {
            Log.d(TAG, "Error inside loadNotes NoteViewModel: $e")
        } finally {
            inProgress = false
        }
    }

    fun getNote(
        noteId: String,
    ) {
        try {
            repository.getNote(noteId) { result ->
                inProgress = true
                noteUiState = result
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error inside getNote NoteViewModel: $e")
        } finally {
            inProgress = false
        }
    }

    fun updateNote(
        noteId: String,
        onNavigate: () -> Unit
    ) {
        try {
            repository.updateNote(
                noteId = noteId,
                brushIndex = noteUiState.brushIndex,
                title = noteUiState.title,
                description = noteUiState.description
            )
            onNavigate.invoke()
        } catch (e: Exception) {
            Log.d(TAG, "Error inside updateNote NoteViewModel: $e")
        }
    }

    fun deleteNote(
        noteId: String,
        onNavigate: () -> Unit
    ) {
        try {
            repository.deleteNote(noteId = noteId)
            onNavigate.invoke()
        } catch (e: Exception) {
            Log.d(TAG, "Error inside deleteNote NoteViewModel: $e")
        }
    }

    fun singOut(onNavigate: () -> Unit) {
        auth.signOut()
        onNavigate.invoke()
    }


}