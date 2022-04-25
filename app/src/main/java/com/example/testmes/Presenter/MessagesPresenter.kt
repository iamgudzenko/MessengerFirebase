package com.example.testmes.Presenter

import android.util.Log
import com.example.testmes.Model.Chats
import com.example.testmes.Model.Messages
import com.example.testmes.View.IMessageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MessagesPresenter(val messageView: IMessageView) : IMessagesPresenter {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    var ref = database.reference

    override fun loadingMessages(loginUserChatWith:String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").child(auth.currentUser?.uid.toString()).child("Chats").orderByChild("loginUserChatWith").equalTo(loginUserChatWith).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.children.count() == 0) {
                    messageView.loadingMessagesError("Empty!")
                }
                for (ds in dataSnapshot.children) {
                    val chats: Chats? = ds.getValue(Chats::class.java)
                    messageView.loadingMessagesSuccess(chats?.listMessages as MutableList<Messages>)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageView.loadingMessagesError(error.toString())
            }

        })
    }

    override fun sendMessage(loginUserChatWith:String, loginUserOwner: String, textMessage: String) {
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").child(auth.currentUser?.uid.toString()).child("Chats").orderByChild("loginUserChatWith").equalTo(loginUserChatWith).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val chats: Chats? = ds.getValue(Chats::class.java)
                    val messages: Messages = Messages(loginUserOwner, textMessage)
                    //chats?.listMessages?.toMutableList()?.add(messages)
                    val c = chats?.listMessages?.size ?: 0
                    writeChats(messages, c, ds)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                messageView.loadingMessagesError(error.toString())
            }

        })
    }
    fun writeChats(messages: Messages, c: Int?, ds:DataSnapshot){
        ds.ref.child("listMessages").child(c.toString()).setValue(messages)
    }

}