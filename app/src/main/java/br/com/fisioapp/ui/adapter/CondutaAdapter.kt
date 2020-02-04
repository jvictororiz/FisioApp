package br.com.fisioapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.Conduta


class CondutaAdapter(var listCondutas: ArrayList<Conduta>) : RecyclerView.Adapter<CondutaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_conduta, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listCondutas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCondutas[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val edtConduta: EditText = view.findViewById(R.id.edt_conduta)

        fun bind(conduta: Conduta) {
            edtConduta.setText(conduta.description)
        }
    }
}
