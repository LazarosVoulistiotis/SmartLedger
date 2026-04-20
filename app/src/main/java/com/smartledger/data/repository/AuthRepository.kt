package com.smartledger.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.smartledger.data.model.UserProfile
import kotlinx.coroutines.tasks.await

// Repository για authentication και αποθήκευση βασικού user profile
class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Επιστρέφει τον τρέχοντα συνδεδεμένο χρήστη, αν υπάρχει
    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    // Δημιουργεί νέο account και αποθηκεύει profile στο Firestore
    suspend fun register(
        email: String,
        password: String,
        displayName: String
    ): Result<FirebaseUser> = runCatching {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val user = authResult.user ?: error("User creation failed.")

        val userProfile = UserProfile(
            uid = user.uid,
            email = user.email ?: email,
            displayName = displayName,
            createdAt = System.currentTimeMillis()
        )

        firestore
            .collection("users")
            .document(user.uid)
            .set(userProfile)
            .await()

        user
    }

    // Πραγματοποιεί login με email και password
    suspend fun login(
        email: String,
        password: String
    ): Result<FirebaseUser> = runCatching {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        authResult.user ?: error("Login failed.")
    }

    // Τερματίζει το τρέχον session του χρήστη
    fun logout() {
        auth.signOut()
    }
}