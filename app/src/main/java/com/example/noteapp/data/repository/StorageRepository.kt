package com.example.noteapp.data.repository

import android.util.Log
import com.example.noteapp.presentation.detail.DetailUIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class StorageRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    private val TAG = StorageRepository::class.simpleName

    private val storage = firebaseFirestore.collection("Notes")
    fun user() = firebaseAuth.currentUser?.uid
    fun addNote(
        userId: String,
        brushIndex: Int = 0,
        title: String = "",
        description: String = "",
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = storage.document().id
        val notes = hashMapOf(
            "userId" to userId,
            "brushIndex" to brushIndex,
            "title" to title,
            "description" to description,
            "documentId" to documentId
        )
        storage.add(notes)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e(TAG, "Added data, Success:${it.isSuccessful}")
                    onComplete.invoke(true)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding data:$e")
                onComplete.invoke(false)
            }
    }

    suspend fun getNotes(userId: String): Flow<List<DetailUIState>> = callbackFlow {
        storage.whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                try {
                    val notes = result?.toObjects(DetailUIState::class.java) ?: emptyList()
                    trySend(notes)
                    Log.e("TAG", "Loaded data, Success")
                } catch (e: Exception) {
                    Log.e(TAG, "Error converting data:$e")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error loading data:$e")
            }
        awaitClose ()
    }


    fun getNote(
        noteId: String,
        onComplete: (DetailUIState) -> Unit
    ) {
        storage.whereEqualTo("documentId", noteId)
            .get()
            .addOnSuccessListener {
                val note = it?.toObjects(DetailUIState::class.java) ?: emptyList()
                onComplete.invoke(note.first())
                Log.e(TAG, "Success get note")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error get note:$e")
            }
    }

    fun updateNote(
        noteId: String = "",
        brushIndex: Int = 0,
        title: String = "",
        description: String = "",
    ) {
        val notes = hashMapOf(
            "brushIndex" to brushIndex,
            "title" to title,
            "description" to description,
        )
        storage.whereEqualTo("documentId", noteId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    storage.document(document.id)
                        .set(notes, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot added with ID: ${document.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Data not updated", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Query failed", e)
            }
    }

    fun deleteNote(
        noteId: String = ""
    ) {
        storage.whereEqualTo("documentId", noteId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    storage.document(document.id)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Record with ID: ${document.id} deleted")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Data not deleted", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Query failed", e)
            }
    }
}




