package com.example.firebaseapp.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : AuthRepository {

    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    override fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun addAuthStateListener(listener: (FirebaseUser?) -> Unit) {
        val firebaseListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            listener(firebaseAuth.currentUser)
        }
        authStateListener = firebaseListener
        auth.addAuthStateListener(firebaseListener)
    }

    override fun removeAuthStateListener() {
        authStateListener?.let { auth.removeAuthStateListener(it) }
        authStateListener = null
    }
}