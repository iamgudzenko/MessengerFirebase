package com.example.testmes.View

import com.example.testmes.Model.Chats

interface ChatsView {
    fun listChatsUser(chat: Chats?)
    fun loadingChatsErrors(message:String)
}