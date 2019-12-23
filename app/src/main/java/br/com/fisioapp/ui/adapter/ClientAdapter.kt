package br.com.fisioapp.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.User


class ClientAdapter() : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {
    private var listClients: MutableList<User> = mutableListOf()
    private var currentIndexColor: Int = 0


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

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val array: Array<String> = view.context.resources.getStringArray(R.array.colors)
        private val cvBody = view.findViewById<CardView>(R.id.cv_body)
        private val tvName = view.findViewById<TextView>(R.id.tv_name)
        private val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        private val progressLimite:ProgressBar = view.findViewById<ProgressBar>(R.id.progress_limite)

        fun bind(user: User) {
            if (currentIndexColor == array.size - 1) currentIndexColor = 0
            cvBody.setCardBackgroundColor(Color.parseColor(array[currentIndexColor]))
            if (currentIndexColor % 2 == 0) {
                ViewCompat.setBackgroundTintList(progressLimite,ColorStateList.valueOf( ContextCompat.getColor(view.context,R.color.background_gradient_final)))
                tvName.background = (ContextCompat.getDrawable(view.context, R.drawable.shape_button_line_circle_final))
            }else{
                ViewCompat.setBackgroundTintList(progressLimite,ColorStateList.valueOf( ContextCompat.getColor(view.context,R.color.colorPrimary)))
                tvName.background = (ContextCompat.getDrawable(view.context, R.drawable.shape_button_line_circle_gradient))
            }

            tvName.text = user.name
            tvUsername.text = user.username
            progressLimite.progress = 50
        }
    }

    fun submitList(clientList: MutableList<User>) {
        this.listClients.clear()
        this.listClients = clientList
        notifyDataSetChanged()
    }
}
