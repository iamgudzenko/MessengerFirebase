package com.example.testmes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testmes.Model.Messages
import com.example.testmes.databinding.ItemMessageBinding

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var messages: List<Messages?> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MessageViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)

        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        with(holder.binding) {
            holder.itemView.tag = message
            textMessage.text = message!!.textMessage
            loginUserMessage.text = message.loginUserOwner
            photoImageViewMessage.setImageResource(R.drawable.user)

        }
    }

    override fun getItemCount() = messages.size
}