package com.example.testmes.Presenter

interface IMessagesPresenter {
    fun loadingMessages(loginUserChatWith:String, loginCurrentUser:String)
    fun sendMessage(loginUserChatWith:String, loginUserOwner:String, textMessage:String)
}