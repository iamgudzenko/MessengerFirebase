package com.example.testmes.Model

import com.google.firebase.Timestamp

data class Messages(val loginUserOwner:String? = null, val textMessage:String? = null, val timeSend:Timestamp? = null) {
}