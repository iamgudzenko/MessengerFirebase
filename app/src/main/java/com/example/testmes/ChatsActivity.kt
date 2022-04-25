package com.example.testmes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmes.Model.Chats
import com.example.testmes.Model.Users
import com.example.testmes.Presenter.*
import com.example.testmes.View.ChatsView
import com.example.testmes.View.ISearchUserView
import com.example.testmes.databinding.ActivityChatsBinding
import com.example.testmes.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatsActivity : AppCompatActivity(), ChatsView, ISearchUserView {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChatsBinding
    private lateinit var adapter: ChatsAdapter
    lateinit var chatsPresenter: IChatsPresenter
    lateinit var searchUser: ISearchUser

    var chatsUser: MutableList<Chats?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatsPresenter = ChatsPresenter(this, this)
        chatsPresenter.loadingChats()
        searchUser = SearchUser(this)

//        val test = TestWriteDb()
//        test.writeToDbTestChat()
//        test.writeToDbTestUser()

        adapter = ChatsAdapter(object : ChatsActionListener{
            override fun goToMessages(chat: Chats) {
                Log.w("LoginUser", chat.loginUserChatWith.toString())
                val intent = Intent(this@ChatsActivity, MessageActivity::class.java)
                intent.putExtra("loginUserChatWith", chat.loginUserChatWith.toString())
                startActivity(intent)
            }

        })

        adapter.chats = chatsUser
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChats.layoutManager = layoutManager
        binding.recyclerViewChats.adapter = adapter

        binding.butSearchUser.setOnClickListener {
            searchUser.userSearch(binding.editSearchPhoneNumberUser.text.toString())
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun listChatsUser(chat: Chats?) {
        Log.w("CHAT ID", chat?.loginUserChatWith.toString())
        chatsUser.add(chat)
        adapter.notifyDataSetChanged()
    }

    override fun loadingChatsErrors(message: String) {
        Toast.makeText(this@ChatsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun searchUserSuccess(user: Users?) {
        Toast.makeText(this@ChatsActivity, "User phone ${user?.phoneNumber}", Toast.LENGTH_SHORT).show()
    }

    override fun searchUserError(message: String) {
        Toast.makeText(this@ChatsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}