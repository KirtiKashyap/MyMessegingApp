package com.example.mytest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.mytest.databinding.ItemConversationBinding
import com.example.mytest.model.response.conversation.ListData
import com.example.mytest.model.response.conversation.Messages
import com.example.mytest.model.response.conversation.Participants

class ConversationAdapter(private val listener: ConversationItemListener) : RecyclerView.Adapter<ConversationViewHolder>() {

    interface ConversationItemListener {
        fun onClickedConversation(conversationList: Int, participentId: Int?)
    }

    private val participantList = ArrayList<Participants>()
    private val messageList = ArrayList<Messages>()
    private val items= ArrayList<ListData>()
    fun setItems(items: ArrayList<ListData>) {
        this.items.clear()
        this.items.addAll(items)
        this.participantList.clear()
        this.messageList.clear()
        for(ParticiPentAndMessage in items){
            this.participantList.addAll(ParticiPentAndMessage.participants)
            this.messageList.addAll(ParticiPentAndMessage.messages!!)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val binding: ItemConversationBinding = ItemConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConversationViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = participantList.size

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) = holder.bind(participantList!![position]!!,messageList!![position],items)
}

class ConversationViewHolder(private val itemBinding: ItemConversationBinding,
                             private val listener: ConversationAdapter.ConversationItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var participents : Participants
    private lateinit var messages: Messages
    private lateinit var conversationList: ListData
    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(participants: Participants, messages: Messages, conversationList: ArrayList<ListData>) {
        this.participents = participants
        this.messages=messages
        this.conversationList=conversationList[0]

        itemBinding.tvName.text = participants.name
        itemBinding.tvMessage.text=messages.message

        Glide.with(itemBinding.root)
            .load(participants.photo)
            .transform(CircleCrop())
            .into(itemBinding.ivImage)
    }

    override fun onClick(v: View?) {
        listener.onClickedConversation(conversationList.id,participents.id)
    }
}