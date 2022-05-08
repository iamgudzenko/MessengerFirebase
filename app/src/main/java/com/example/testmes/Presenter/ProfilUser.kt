package com.example.testmes.Presenter

import android.util.Log
import com.example.testmes.Model.Chats
import com.example.testmes.Model.Users
import com.example.testmes.View.IProfilUserView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User

class ProfilUser(val profilUserView: IProfilUserView) : IProfilUser {
    val mAuth= FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun exitProfilUser() {
        mAuth.signOut()
        profilUserView.exitUser()
    }

    override fun getProfilUser() {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").orderByChild("phoneNumber").equalTo(mAuth.currentUser?.phoneNumber).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val user: Users? = ds.getValue(Users::class.java)
                    profilUserView.getProfilUserSuccess(user)
                    break
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                profilUserView.getProfilUserError("Failed to read value." + error.toException())
            }
        })
    }
}