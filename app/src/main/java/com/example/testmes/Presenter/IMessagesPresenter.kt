package com.example.testmes.Presenter

interface IMessagesPresenter {
    fun loadingMessages(loginUserChatWith:String)
    fun sendMessage(loginUserChatWith:String, loginUserOwner:String, textMessage:String)
}