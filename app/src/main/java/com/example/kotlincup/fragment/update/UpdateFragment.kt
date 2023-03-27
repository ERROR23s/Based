package com.example.kotlincup.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlincup.R
import com.example.kotlincup.data.User
import com.example.kotlincup.data.UserViewModel
import com.example.kotlincup.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private lateinit var bind:FragmentUpdateBinding
    private  val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentUpdateBinding.inflate(inflater, container,false)

        bind.upNameTxt.setText(args.currentUser.firstName)
        bind.upFamTxt.setText(args.currentUser.lastName)
        bind.upAgeTxt.setText(args.currentUser.age.toString())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        bind.upSaveBtn.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return bind.root
    }

    private fun updateItem() {
        val firstName = bind.upNameTxt.text.toString()
        val lastName = bind.upFamTxt.text.toString()
        val age = bind.upAgeTxt.text

        if(inputCheck(firstName,lastName,age)){

            val user = User(args.currentUser.id,firstName,lastName,age.toString().toInt())
            mUserViewModel.updateUser(user)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

            Toast.makeText(requireContext(),"Изменения внесены в БД", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(),"Заполните все поля", Toast.LENGTH_SHORT).show()
        }


    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(firstName.isEmpty()|| lastName.isEmpty() || age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteUser()
        }

        return super.onOptionsItemSelected(item)

    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да"){ _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),"Запись успешно удалена", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Хотите удалить? :")
        builder.setMessage("Are u sure about delete this shit: ${args.currentUser.firstName}?")
        builder.create().show()
    }

}