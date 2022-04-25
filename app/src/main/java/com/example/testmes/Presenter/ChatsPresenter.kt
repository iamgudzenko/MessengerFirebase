package com.example.testmes.Presenter

import android.app.Activity
import android.util.Log
import com.example.testmes.Model.Chats
import com.example.testmes.View.ChatsView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatsPresenter (val chatsView: ChatsView, val activity: Activity) : IChatsPresenter{
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun loadingChats() {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").child(auth.currentUser?.uid.toString()).child("Chats").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val chat: Chats? = ds.getValue(Chats::class.java)
                    Log.w("CHAT ID", chat?.loginUserChatWith.toString())
                    chatsView.listChatsUser(chat)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Failed to read value.", error.toException())
                chatsView.loadingChatsErrors("Failed to read value." + error.toException())
            }
        })
    }
}