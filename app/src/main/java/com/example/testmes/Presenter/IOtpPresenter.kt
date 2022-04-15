package com.example.testmes.Presenter

import com.google.firebase.auth.PhoneAuthCredential

interface IOtpPresenter {
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)


}