package com.example.kotlincup.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.kotlincup.R
import com.example.kotlincup.data.UserViewModel
import com.example.kotlincup.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var bind:FragmentListBinding
    private lateinit var  mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        bind = FragmentListBinding.inflate(inflater, container,false)//все что тут это же было снизу если что

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val adapter = ListAdapter()
        val recyclerView = bind.recyclerView
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer{listuser ->
            adapter.setData(listuser)
        })

        bind.PlusBtn.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return bind.root
    }
}