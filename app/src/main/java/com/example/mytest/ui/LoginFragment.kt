package com.example.mytest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytest.R
import com.example.mytest.model.request.TokenRequest
import com.example.mytest.utils.ResourceHelper
import com.example.mytest.utils.SharedPref
import com.example.mytest.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.progressBar

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel : AuthViewModel
    private lateinit var sharedPreference :SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(AuthViewModel::class.java)
        sharedPreference= activity?.let { SharedPref(it.applicationContext) }!!
        setHasOptionsMenu(true)
        loginButton.setOnClickListener {
            val tokenRequest= TokenRequest(email = userName.text.toString(),password = passWord.text.toString())
            viewModel.getToken(tokenRequest)
            setupObservers()
            observeUserProfile()
        }
    }

    private fun observeUserProfile() {
        viewModel.userProfile.observe(viewLifecycleOwner,{
            when(it.status) {
                ResourceHelper.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.id?.let { userId ->
                        sharedPreference?.save("USER_ID", userId)
                    }
                }

                ResourceHelper.Status.ERROR ->
                    progressBar.visibility = View.GONE

                ResourceHelper.Status.LOADING ->
                    Log.d("","")
                //progressBar.visibility = View.VISIBLE
            }

        })
    }

    private fun setupObservers() {
        viewModel.tokenData.observe(viewLifecycleOwner, Observer { it ->
            when (it.status) {
                ResourceHelper.Status.SUCCESS -> {

                    progressBar.visibility = View.GONE
                    it.data?.let {
                        it.is_verified
                        val token=it.token_type+" "+it.access_token
                        sharedPreference?.save("APP_PREF_TOKEN",token)
                        closeActivity()
                    }

                }
                ResourceHelper.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                ResourceHelper.Status.LOADING ->
                    progressBar.visibility = View.VISIBLE
            }
        })
    }
    private fun closeActivity(){
        activity?.let{
            val intent = Intent (activity, ContainerActivity::class.java)
            it.finish()
            it.startActivity(intent)
        }
    }

}