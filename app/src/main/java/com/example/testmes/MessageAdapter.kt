package com.example.testmes

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackgroundTintList
import androidx.recyclerview.widget.RecyclerView
import com.example.testmes.Model.Messages
import com.example.testmes.databinding.ItemMessageBinding
import java.text.SimpleDateFormat
import java.util.*


class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var messages: List<Messages?> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    var userCurrentLogin: String? = null
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
        val set = ConstraintSet()

        with(holder.binding) {
            holder.itemView.tag = message
            val time = message?.timeSend.toString()
            var lTime:Long = 0
            if(time == "null") {
                lTime = 0
            } else {
                lTime = time.toLong()
            }
            Log.w("TIME", lTime.toString())
            val date = Date(lTime)
            val format = SimpleDateFormat("HH:mm")
            if(userCurrentLogin == message!!.loginUserOwner){
                set.clone(messageItemLayout)
                set.clear(R.id.messageLayoutText, ConstraintSet.START)
                set.connect(R.id.messageLayoutText, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                set.applyTo(messageItemLayout)
                textMessage.text = message!!.textMessage
                messageLayoutText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E8DAF8")))
                timeMessage.text = format.format(date).toString()
            } else {
                set.clone(messageItemLayout)
                set.clear(R.id.messageLayoutText, ConstraintSet.END)
                set.connect(R.id.messageLayoutText, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                set.applyTo(messageItemLayout)
                textMessage.text = message!!.textMessage
                messageLayoutText.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D6F1FF")))
                timeMessage.text = format.format(date).toString()
            }

        }
    }

    override fun getItemCount() = messages.size
}