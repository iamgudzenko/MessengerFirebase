package com.example.testmes.Presenter

import android.graphics.ColorSpace.Model
import android.util.Log
import com.example.testmes.Model.Chats
import com.example.testmes.Model.Users
import com.example.testmes.View.ISearchUserView
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchUser(val searchView: ISearchUserView) : ISearchUser {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun userSearch(phoneNumber: String) {
        ref.child("Users").orderByChild("phoneNumber").equalTo(phoneNumber).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("SEARCH", dataSnapshot.children.count().toString())
                if(dataSnapshot.children.count() == 0) {
                    searchView.searchUserError("Empty!")
                }
                for (ds in dataSnapshot.children) {

                    val user: Users? = ds.getValue(Users::class.java)
                    Log.w("User", user?.phoneNumber.toString())
                    searchView.searchUserSuccess(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                searchView.searchUserError(error.toString())
            }

        })
    }

}