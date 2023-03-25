package com.example.kotlincup.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincup.R
import com.example.kotlincup.data.User
import java.lang.reflect.Type

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = userList[position]
        val id_txt = holder.itemView.findViewById<TextView>(R.id.idTxt)
        val name_txt = holder.itemView.findViewById<TextView>(R.id.nameTxt)
        val fam_txt = holder.itemView.findViewById<TextView>(R.id.famTxt)
        val age_txt = holder.itemView.findViewById<TextView>(R.id.ageTxt)
        val rowlayot_txt = holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout)

        id_txt.text = currentUser.id.toString()
        name_txt.text = currentUser.firstName
        fam_txt.text = currentUser.lastName
        age_txt.text = currentUser.age.toString()
        rowlayot_txt.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentUser)
            holder.itemView.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user:List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}