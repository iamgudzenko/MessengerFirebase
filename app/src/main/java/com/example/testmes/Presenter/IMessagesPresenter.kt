package com.example.testmes.Presenter

interface IMessagesPresenter {
    fun loadingMessages(loginUserChatWith:String, loginCurrentUser:String)
    fun sendMessage(loginUserChatWith:String, loginUserOwner:String, textMessage:String)
    fun stateMessages(loginUserChatWith:String, loginCurrentUser:String)
    fun readingMessages(loginUserChatWith:String, loginCurrentUser:String)
}