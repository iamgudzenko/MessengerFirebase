package com.example.testmes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.testmes.Presenter.IOtpPresenter
import com.example.testmes.Presenter.IUserWriteDB
import com.example.testmes.Presenter.UserWriteDB
import com.example.testmes.View.UserToDBView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), UserToDBView {
    lateinit var auth: FirebaseAuth
    lateinit var userWriteDB: IUserWriteDB
    lateinit var userLogin: EditText
    lateinit var textInfo:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userPhoneNumber = user?.phoneNumber
        userWriteDB = UserWriteDB(auth, this, this)
        userLogin = findViewById(R.id.editLogin)
        textInfo = findViewById(R.id.textInfoLogin)

        findViewById<Button>(R.id.butLogin).setOnClickListener {
            userWriteDB.writeToDbUser(userPhoneNumber.toString(), userLogin.text.toString())
        }

    }

    override fun writeUserDbSuccess(message: String) {
        startActivity(Intent(applicationContext, ChatsActivity::class.java))
        finish()
    }

    override fun writeUserDbError(message: String) {
        textInfo.setText(message)
    }

}