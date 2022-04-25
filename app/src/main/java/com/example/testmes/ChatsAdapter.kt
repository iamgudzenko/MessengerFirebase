package com.example.testmes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testmes.Model.Chats
import com.example.testmes.databinding.ItemChatsBinding
import com.google.firebase.firestore.auth.User


interface ChatsActionListener {
    fun goToMessages(chat: Chats)
}

class ChatsAdapter(
    private val actionListener: ChatsActionListener
    ) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>(), View.OnClickListener {

    var chats: List<Chats?> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class ChatsViewHolder(val binding: ItemChatsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatsBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        return ChatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val chat = chats[position]
        with(holder.binding) {
            holder.itemView.tag = chat
            userLoginChats.text = chat!!.loginUserChatWith
            lastMessageChats.text = chat.listMessages.last().textMessage
            photoImageView.setImageResource(R.drawable.user)
        }
    }

    override fun getItemCount(): Int = chats.size

    override fun onClick(v: View) {
        val chat = v.tag as Chats
        actionListener.goToMessages(chat)
    }
}