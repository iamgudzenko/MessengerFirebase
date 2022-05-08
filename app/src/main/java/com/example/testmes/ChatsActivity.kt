package com.example.testmes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmes.Model.Chats
import com.example.testmes.Model.Users
import com.example.testmes.Presenter.*
import com.example.testmes.View.ChatsView
import com.example.testmes.View.IProfilUserView
import com.example.testmes.View.ISearchUserView
import com.example.testmes.databinding.ActivityChatsBinding
import com.google.android.material.navigation.NavigationView


class ChatsActivity : AppCompatActivity(), ChatsView, ISearchUserView, IProfilUserView {
    private lateinit var binding: ActivityChatsBinding
    private lateinit var adapter: ChatsAdapter
    lateinit var chatsPresenter: IChatsPresenter
    lateinit var profilUserPresenter: IProfilUser
    lateinit var searchUser: ISearchUser
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var toolbar: Toolbar
    var userProfilCurrent: Users? = null

    var chatsUser: MutableList<Chats?> = mutableListOf()
    var chatsMap: MutableMap<String?, Chats?> = mutableMapOf()

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide()
        profilUserPresenter = ProfilUser(this)
        profilUserPresenter.getProfilUser()
        nawigation()

        chatsPresenter = ChatsPresenter(this, this)
        searchUser = SearchUser(this)

//        val test = TestWriteDb()
//        test.writeToDbTestChat()
//        test.writeToDbTestUser()

        adapter = ChatsAdapter(object : ChatsActionListener {
            override fun goToMessages(chat: Chats) {
                Log.w("LoginUser", chat.loginUserChatWith.toString())
                val intent = Intent(this@ChatsActivity, MessageActivity::class.java)
                intent.putExtra("loginUserChatWith", chat.loginUserChatWith.toString())
                intent.putExtra("loginUserCurrent", userProfilCurrent?.userLogin.toString())
                startActivity(intent)
            }

        })
        binding.butSearchUser.setOnClickListener {
            searchUser.userSearch(binding.editSearchPhoneNumberUser.text.toString())
            binding.editSearchPhoneNumberUser.setText("")
        }

        binding.editSearchPhoneNumberUser.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.recyclerViewChats.setVisibility(View.GONE)
                binding.textInfoSearch.setVisibility(View.VISIBLE)
            } else {
                binding.recyclerViewChats.setVisibility(View.VISIBLE)
                binding.textInfoSearch.setVisibility(View.GONE)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {
        binding.editSearchPhoneNumberUser.clearFocus()
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun nawigation() {

        drawLayout = findViewById(R.id.drawLayout)
        toolbar = findViewById(R.id.toolbar)
        toggle = ActionBarDrawerToggle(this, drawLayout, toolbar, R.string.open, R.string.close)
        navView = findViewById(R.id.navView)

        drawLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.exitProfilUser -> profilUserPresenter.exitProfilUser()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun listChatsUser(chat: Chats?) {
        if(chatsMap.containsKey(chat?.loginUserChatWith)){
            chatsMap.remove(chat?.loginUserChatWith)
            chatsMap[chat?.loginUserChatWith] = chat
        } else {
            chatsMap[chat?.loginUserChatWith] = chat
        }
        adapter.chats = chatsMap.values.toMutableList()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChats.layoutManager = layoutManager
        binding.recyclerViewChats.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        chatsMap.clear()
        binding.recyclerViewChats.removeAllViews()
        chatsPresenter.loadingChats(userProfilCurrent?.userLogin ?: " ")
        super.onResume()
    }

    fun updateChats(){
        chatsMap.clear()
        binding.recyclerViewChats.removeAllViews()
        chatsPresenter.loadingChats(userProfilCurrent?.userLogin.toString())
    }
    override fun loadingChatsErrors(message: String) {
        Toast.makeText(this@ChatsActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }


    override fun searchUserSuccess(user: Users?) {
        Log.w("LoginUser", user?.userLogin.toString())
        val intent = Intent(this@ChatsActivity, MessageActivity::class.java)
        intent.putExtra("loginUserChatWith", user?.userLogin.toString())
        intent.putExtra("loginUserCurrent", userProfilCurrent?.userLogin.toString())
        startActivity(intent)
        Toast.makeText(this@ChatsActivity, "User phone ${user?.phoneNumber}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun searchUserError(message: String) {
        binding.recyclerViewChats.setVisibility(View.GONE)
        binding.textInfoSearch.setVisibility(View.VISIBLE)
        binding.textInfoSearch.setText("Такого пользователя нет, возможно он не пользуется нашим приложением")
    }

    override fun exitUser() {
        Toast.makeText(applicationContext, "You exit", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@ChatsActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getProfilUserSuccess(userCurrent: Users?) {
        userProfilCurrent = userCurrent!!
        val menu: Menu = navView.getMenu()
        val nav_login = menu.findItem(R.id.loginCurrentUser)
        val nav_phone = menu.findItem(R.id.phoneNumberCurrentUser)
        nav_login.setTitle(userProfilCurrent?.userLogin.toString())
        nav_phone.setTitle(userProfilCurrent?.phoneNumber.toString())
        updateChats()
    }

    override fun getProfilUserError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}