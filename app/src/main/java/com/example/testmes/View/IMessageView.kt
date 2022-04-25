package com.example.testmes.View

import com.example.testmes.Model.Messages

interface IMessageView {
    fun loadingMessagesSuccess(listMessage: MutableList<Messages>)
    fun loadingMessagesError(message:String)
}