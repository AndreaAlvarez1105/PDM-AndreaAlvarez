package com.example.firebaseapp.data.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun createUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signOut()
    fun addAuthStateListener(listener: (FirebaseUser?) -> Unit)
    fun removeAuthStateListener()
}