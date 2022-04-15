package com.example.testmes.Presenter

import android.app.Activity
import com.example.testmes.Model.Users
import com.example.testmes.View.UserToDBView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserWriteDB(val mAuth: FirebaseAuth, val userToDBView: UserToDBView, val activity: Activity):IUserWriteDB {
    var database = FirebaseDatabase.getInstance()

    override fun writeToDbUser(phoneNumber: String, loginUser: String) {
        val user = Users(phoneNumber, loginUser)

        if(phoneNumber.isEmpty() || loginUser.isEmpty()) {
            userToDBView.writeUserDbError("Invalid login")
        } else {
            var ref = database.reference.child("Users").child(Firebase.auth.currentUser?.uid.toString())
            ref.push().setValue(user)

            userToDBView.writeUserDbSuccess("Успешно зарегистрировались")
        }
    }
}