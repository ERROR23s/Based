package com.example.kotlincup.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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


        setHasOptionsMenu(true)

        return bind.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_all_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteAllUser()
        }

        return super.onOptionsItemSelected(item)

    }
    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да"){ _, _ ->
            mUserViewModel.deleteAllUser()
            Toast.makeText(requireContext(),"Запись успешно удалена", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Хотите удалить? :")
        builder.setMessage("Are u sure about delete ALL this shit from db?")
        builder.create().show()
    }
}