package br.com.fisioapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.TreinoResponse

class TreinoAdapter() : RecyclerView.Adapter<TreinoAdapter.ViewHolder>(){
    private var listTreinos: MutableList<TreinoResponse> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_treino, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listTreinos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTreinos[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(treino: TreinoResponse) {

        }
    }

    fun submitList(listTreinos: MutableList<TreinoResponse>){
        this.listTreinos.clear()
        this.listTreinos = listTreinos
        notifyDataSetChanged()
    }
}
