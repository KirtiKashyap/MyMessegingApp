package com.example.mytest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.R
import com.example.mytest.model.response.conversation.Messages
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*


private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class MessageAdapter(private val participantId: Int, private val userId: Int) : RecyclerView.Adapter<MessageViewHolder>() {
    private val messages: ArrayList<Messages> = ArrayList()

    fun addMessage(message: Messages){
        messages.add(message)
        notifyDataSetChanged()
    }

    fun setItems(items: ArrayList<Messages>) {
        messages.clear()
        messages.addAll(items)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {

        return if(userId == messages[position].sender) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if(viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_message, parent, false))
        } else {
            OtherMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.other_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.bind(message)
    }

    inner class MyMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime
        private var userText: TextView = view.txtMyUser
        override fun bind(message: Messages) {
            messageText.text = message.message
            userText.text=message.sent
            timeText.visibility =View.GONE
        }
    }

    inner class OtherMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: Messages) {
            messageText.text = message.message
            userText.text=message.sent
            timeText.visibility = View.GONE
        }
    }
}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Messages) {}
}