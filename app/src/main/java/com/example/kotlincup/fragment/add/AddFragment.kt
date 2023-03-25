package com.example.kotlincup.fragment.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kotlincup.R
import com.example.kotlincup.data.User
import com.example.kotlincup.data.UserViewModel
import com.example.kotlincup.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var bind:FragmentAddBinding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentAddBinding.inflate(inflater, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        bind.SaveBtn.setOnClickListener {
            insertDataToDB()
        }

        return bind.root //inflater.inflate(R.layout.fragment_add, container, false)
    }

    private fun insertDataToDB() {
        val firstName = bind.NameTxt.text.toString()
        val lastName = bind.FamTxt.text.toString()
        val age = bind.AgeTxt.text

        if(inputCheck(firstName,lastName,age)){
            val user = User(0,firstName,lastName,age.toString().toInt())
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(),"Запись добавлена в БД", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else Toast.makeText(requireContext(),"Заполните все поля", Toast.LENGTH_SHORT).show()

    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(firstName.isEmpty()|| lastName.isEmpty() || age.isEmpty())
    }

}