package com.example.testmes.Model
import com.google.firebase.Timestamp

data class Chats(var phoneUserChatWith:String? = null, var loginUserChatWith:String? = null, var listMessages: List<Messages> = emptyList()) {
}