package com.example.mytest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytest.R
import com.example.mytest.adapter.ConversationAdapter
import com.example.mytest.utils.ResourceHelper
import com.example.mytest.utils.SharedPref
import com.example.mytest.viewmodel.ConversationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_conversation.*

@AndroidEntryPoint
class ConversationFragment : Fragment(R.layout.fragment_conversation),ConversationAdapter.ConversationItemListener {
    private lateinit var viewModel : ConversationViewModel
    private lateinit var conversationadapter: ConversationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(ConversationViewModel::class.java)
        val sharedPreference= activity?.let { SharedPref(it.applicationContext) }
        val token=sharedPreference?.getValueString("APP_PREF_TOKEN")
        viewModel.start(token!!)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.conversationList.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                ResourceHelper.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it?.let {
                        recyclerView.visibility = View.VISIBLE
                        setupRecyclerView()
                        if (!it.data.isNullOrEmpty()) conversationadapter.setItems(ArrayList(it.data))
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
        conversationadapter = ConversationAdapter(this)
        recyclerView.apply {
            adapter = conversationadapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onClickedConversation(conversationId: Int, participentId: Int?) {
        findNavController().navigate(
            R.id.action_conversationFragment_to_messageFragment,
            bundleOf("conversationId" to conversationId,"participentId" to participentId)
        )
    }
}