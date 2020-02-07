package br.com.fisioapp.ui.sheetDialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.Sessao
import br.com.fisioapp.ui.components.SuperSheetPopup
import br.com.fisioapp.util.ext.isPar
import br.com.fisioapp.util.ext.toString
import kotlinx.android.synthetic.main.bottom_dialog_sessions.view.*
import kotlinx.android.synthetic.main.item_sessao.view.*

class SessionsBottomDialog : SuperSheetPopup {
    private var sessions: List<Sessao> = ArrayList()
    private var fragmeent: Fragment = Fragment()
    private var activity: Activity? = null


    constructor(context: Activity, sessions: List<Sessao>) : super(context) {
        this.activity = context
        this.sessions = sessions
    }

    constructor(context: Fragment) : super(context.requireContext()) {
        this.fragmeent = context
    }

    override fun createView(): View {
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_sessions, null)
        val textDate = sessions[0].date.toString("dd/MM") + " até " + sessions[sessions.size - 1].date.toString("dd/MM")
        v.tv_apresentation_user.text = context.getString(R.string.sessoes_entre, textDate)
        v.rv_sessions.adapter = SessionsAdapter(context, sessions)

        return v
    }


    class SessionsAdapter(val context: Context, var listCondutas: List<Sessao>) : RecyclerView.Adapter<SessionsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sessao, parent, false)
            return ViewHolder(view)

        }

        override fun getItemCount(): Int {
            return listCondutas.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(listCondutas[position])
        }

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(sessao: Sessao) {
                if (adapterPosition.isPar()) {
                    view.line_color.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                } else {
                    view.line_color.setBackgroundColor(ContextCompat.getColor(context, R.color.background_gradient_final))
                }
                view.tv_day.text = sessao.date.toString("dd")
                view.tv_month.text = sessao.date.toString("MMM")
                view.tv_objetive.text = sessao.observacao
                view.tv_avaliation.text = context.getString(R.string.avaliacao, sessao.nota.toString())
            }
        }
    }

}