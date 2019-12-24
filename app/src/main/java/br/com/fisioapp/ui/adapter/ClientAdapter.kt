package br.com.fisioapp.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.User


class ClientAdapter : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {
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
        private val cvBody = view.findViewById<CardView>(R.id.cv_body)
        private val tvName = view.findViewById<TextView>(R.id.tv_name)
        private val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        private val progressLimite:ProgressBar = view.findViewById(R.id.progress_limite)

        fun bind(user: User) {
            prepareColors()
            tvName.text = user.name
            tvUsername.text = user.username
            progressLimite.progress = 50
            currentIndexColor++
        }

        private fun prepareColors() {
            val array: Array<String> = view.context.resources.getStringArray(R.array.colors)
            if (currentIndexColor == array.size - 1)
                currentIndexColor = 0
            cvBody.setCardBackgroundColor(Color.parseColor(array[currentIndexColor]))
            if (currentIndexColor % 2 == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressLimite.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(view.context, R.color.background_gradient_final))
                }
                tvUsername.background = (ContextCompat.getDrawable(view.context, R.drawable.shape_button_line_circle_final))
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressLimite.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(view.context, R.color.colorPrimary))
                }
                tvUsername.background = (ContextCompat.getDrawable(view.context, R.drawable.shape_button_circle_gradient))
            }
        }
    }

    fun submitList(clientList: MutableList<User>) {
        this.listClients.clear()
        this.listClients = clientList
        notifyDataSetChanged()
    }
}
