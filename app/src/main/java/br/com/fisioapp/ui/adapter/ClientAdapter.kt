package br.com.fisioapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.UserResponse

class ClientAdapter() : RecyclerView.Adapter<ClientAdapter.ViewHolder>(){
    private var listClients: MutableList<UserResponse> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listClients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listClients[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(user: UserResponse) {

        }
    }

    fun submitList(clientList: MutableList<UserResponse>){
        this.listClients.clear()
        this.listClients = clientList
        notifyDataSetChanged()
    }
}
