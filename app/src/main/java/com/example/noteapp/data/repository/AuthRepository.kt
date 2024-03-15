package com.example.noteapp.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
){

    private val TAG = AuthRepository::class.simpleName

    suspend fun createUser(
        email:String,
        password:String)
            = withContext(Dispatchers.IO) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    Log.e(TAG, "Create Success:${it.isSuccessful}")
                }
            }.addOnFailureListener{
                Log.e(TAG, "Exception = ${it.localizedMessage}")
            }.await()
    }

    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e(TAG, "Login Success:${it.isSuccessful}")
                    onComplete.invoke(true)
                }
            }.addOnFailureListener{
                Log.e(TAG, "Exception = ${it.localizedMessage}")
                onComplete.invoke(false)
            }.await()
    }


    fun signOut() = firebaseAuth.signOut()
}