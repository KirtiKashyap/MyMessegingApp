package com.example.mytest.ui

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytest.R
import com.example.mytest.adapter.MessageAdapter
import com.example.mytest.model.request.SendMessage
import com.example.mytest.model.response.conversation.Messages
import com.example.mytest.utils.ResourceHelper
import com.example.mytest.utils.SharedPref
import com.example.mytest.viewmodel.ConversationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_conversation.progressBar
import kotlinx.android.synthetic.main.fragment_message.*

@AndroidEntryPoint
class MessageFragment : Fragment(R.layout.fragment_message) {
    private lateinit var messageAdapter: MessageAdapter
    private var conversationId =0
    private var participantId=0
    private var userId : Int = 0
    private lateinit var viewModel : ConversationViewModel
    private lateinit var sendMessageRequest : SendMessage
    private lateinit var message : Messages
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this).get(ConversationViewModel::class.java)

        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }
        val token=sharedPreference?.getValueString("APP_PREF_TOKEN")
        userId= sharedPreference?.getValueInt("USER_ID")!!


        conversationId = arguments?.getInt("conversationId")!!
        participantId = arguments?.getInt("participentId")!!
        setupRecyclerView()

        if (token != null) {
            if (conversationId != null) {
                viewModel.startMessageObservation(conversationId,token)
                startObserver()
            }
        }


        btnSend.setOnClickListener {
            val timeInMillis = Calendar.getInstance().time
            sendMessageRequest=SendMessage(message = txtMessage.text.toString(),
                content = txtMessage.text.toString(),sender = participantId,sent =timeInMillis.toString() )
            message= Messages(message = txtMessage.text.toString(),
                sender = userId,
                sent =timeInMillis.toString(),
            )
            messageAdapter.addMessage(message)
            txtMessage.text.clear()

            rvMessageList.smoothScrollToPosition(messageAdapter.itemCount)
            // Hide keyboard
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if (token != null) {
                viewModel.sendMessage(
                    token = token,
                    sendMessage = sendMessageRequest,
                    id = conversationId!!)
                observeSendMessageResponse()
            }

        }


    }

    private fun observeSendMessageResponse() {
        viewModel.sendMessageResponse.observe(viewLifecycleOwner,{
            it->
            when(it.status) {
                ResourceHelper.Status.SUCCESS -> {
                progressBar.visibility = View.GONE
                        Toast . makeText (context, "Message sent success", Toast.LENGTH_SHORT)
                    .show()
            }

                ResourceHelper.Status.ERROR ->
                    progressBar.visibility = View.GONE

                ResourceHelper.Status.LOADING ->
                    progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun startObserver() {
        viewModel.messageList.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                ResourceHelper.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it?.let {
                        rvMessageList.visibility = View.VISIBLE
                        if (!it.data.isNullOrEmpty()) messageAdapter.setItems(
                            ArrayList(
                                it.data
                            )
                        )
                        rvMessageList.smoothScrollToPosition(it.data!!.size)
                    }
                }
                ResourceHelper.Status.ERROR ->
                    progressBar.visibility = View.GONE

                ResourceHelper.Status.LOADING ->
                    progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(participantId,userId)
        rvMessageList.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}