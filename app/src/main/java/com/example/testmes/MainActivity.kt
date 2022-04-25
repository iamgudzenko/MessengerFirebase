package com.example.testmes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.testmes.Presenter.IPhoneAuthPresenter
import com.example.testmes.Presenter.PhoneNumberAuthPresenter
import com.example.testmes.Presenter.TestWriteDb
import com.example.testmes.View.IPhoneAuthView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), IPhoneAuthView {
    lateinit var number:EditText
    lateinit var textInfo:TextView
    lateinit var auth: FirebaseAuth
    lateinit var phonePresenter:IPhoneAuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth=FirebaseAuth.getInstance()
        phonePresenter = PhoneNumberAuthPresenter(auth, this, this)
        number = findViewById(R.id.editNumber)
        textInfo = findViewById(R.id.textInfoPhone)

        phonePresenter.isSignedIn()

        findViewById<Button>(R.id.butSendSms).setOnClickListener {
            phonePresenter.loginPhone(number.getText().toString())
        }
    }

    override fun verificationSuccess(storedVerificationId: String) {
        val intent = Intent(this@MainActivity, OtpActivity::class.java)
        intent.putExtra("storedVerificationId",storedVerificationId)
        Log.d("AAAAAAId1", storedVerificationId)
        startActivity(intent)
        finish()
    }

    override fun loginPhoneError(message: String) {
        textInfo.setText(message)
    }

    override fun afterVerification(message: String) {
        startActivity(Intent(applicationContext, ChatsActivity::class.java))
        finish()
    }

    override fun verificationFailed(message: String) {
        textInfo.setText(message)
    }

    override fun isSignedIn(isSignedIn: Boolean) {
        if(isSignedIn){
            val intent = Intent(this@MainActivity, ChatsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}