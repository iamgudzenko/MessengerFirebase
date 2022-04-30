package com.example.testmes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat.setWindowInsetsAnimationCallback
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmes.Model.Chats
import com.example.testmes.Model.Users
import com.example.testmes.Presenter.*
import com.example.testmes.View.ChatsView
import com.example.testmes.View.ISearchUserView
import com.example.testmes.databinding.ActivityChatsBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class ChatsActivity : AppCompatActivity(), ChatsView, ISearchUserView {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChatsBinding
    private lateinit var adapter: ChatsAdapter
    lateinit var chatsPresenter: IChatsPresenter
    lateinit var searchUser: ISearchUser
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var toolbar: Toolbar

    var chatsUser: MutableList<Chats?> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide();
        nawigation()

        chatsPresenter = ChatsPresenter(this, this)
        chatsPresenter.loadingChats()
        searchUser = SearchUser(this)

//        val test = TestWriteDb()
//        test.writeToDbTestChat()
//        test.writeToDbTestUser()

        adapter = ChatsAdapter(object : ChatsActionListener{
            override fun goToMessages(chat: Chats) {
                Log.w("LoginUser", chat.loginUserChatWith.toString())
                val intent = Intent(this@ChatsActivity, MessageActivity::class.java)
                intent.putExtra("loginUserChatWith", chat.loginUserChatWith.toString())
                startActivity(intent)
            }

        })

        adapter.chats = chatsUser
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChats.layoutManager = layoutManager
        binding.recyclerViewChats.adapter = adapter

        binding.editSearchPhoneNumberUser.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.isFocusableInTouchMode = true
                binding.editSearchPhoneNumberUser.clearFocus()
            }
            v.performClick()
        })

        binding.butSearchUser.setOnClickListener {
            searchUser.userSearch(binding.editSearchPhoneNumberUser.text.toString())
        }

        binding.recyclerViewChats.setOnClickListener {
            binding.editSearchPhoneNumberUser.clearFocus()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {
        binding.editSearchPhoneNumberUser.clearFocus()
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun nawigation(){
        drawLayout = findViewById(R.id.drawLayout)
        toolbar = findViewById(R.id.toolbar)
        toggle = ActionBarDrawerToggle(this, drawLayout, toolbar, R.string.open, R.string.close)
        navView = findViewById(R.id.navView)

        drawLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> Toast.makeText(applicationContext,
                    "Item 1", Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(applicationContext,
                    "Item 2", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(applicationContext,
                    "Item 3", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun listChatsUser(chat: Chats?) {
        Log.w("CHAT ID", chat?.loginUserChatWith.toString())
        chatsUser.add(chat)
        adapter.notifyDataSetChanged()
    }

    override fun loadingChatsErrors(message: String) {
        Toast.makeText(this@ChatsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun searchUserSuccess(user: Users?) {
        Toast.makeText(this@ChatsActivity, "User phone ${user?.phoneNumber}", Toast.LENGTH_SHORT).show()
    }

    override fun searchUserError(message: String) {
        Toast.makeText(this@ChatsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}