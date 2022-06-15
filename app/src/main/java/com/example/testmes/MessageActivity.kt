package com.example.testmes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmes.Model.Messages
import com.example.testmes.Presenter.IMessagesPresenter
import com.example.testmes.Presenter.MessagesPresenter
import com.example.testmes.View.IMessageView
import com.example.testmes.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity(), IMessageView {
    lateinit var messagePresenter: IMessagesPresenter
    private lateinit var binding: ActivityMessageBinding
    lateinit var adapter: MessageAdapter
    lateinit var loginUserCurrent: String
    var messagesChatUser: MutableList<Messages?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide()

        val loginUserChatWith = intent.getSerializableExtra("loginUserChatWith").toString()
        loginUserCurrent = intent.getSerializableExtra("loginUserCurrent").toString()
        binding.loginUserToolBar.setText(loginUserChatWith)
        //kfjdkfgdl

        messagePresenter = MessagesPresenter(this)
        messagePresenter.loadingMessages(loginUserChatWith, loginUserCurrent)
        messagePresenter.readingMessages(loginUserChatWith, loginUserCurrent)

        binding.butSendMessage.setOnClickListener {
            messagePresenter.sendMessage(loginUserChatWith, loginUserCurrent, binding.editTextMessage.text.toString())
            binding.editTextMessage.setText("")
        }

    }

    override fun loadingMessagesSuccess(listMessage: MutableList<Messages>) {
        messagesChatUser = listMessage.toMutableList()
        adapter = MessageAdapter()
        adapter.messages = messagesChatUser
        adapter.userCurrentLogin = loginUserCurrent
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMessages.layoutManager = layoutManager
        binding.recyclerViewMessages.adapter = adapter
        binding.recyclerViewMessages.scrollToPosition(adapter.getItemCount() - 1)

    }

    override fun loadingMessagesError(message: String) {
        Toast.makeText(this@MessageActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun sendMessagesError(message: String) {
        Toast.makeText(this@MessageActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}