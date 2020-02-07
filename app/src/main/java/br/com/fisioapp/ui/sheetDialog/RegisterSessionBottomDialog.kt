package br.com.fisioapp.ui.sheetDialog

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.fisioapp.R
import br.com.fisioapp.data.entities.remote.response.Sessao
import br.com.fisioapp.ui.components.SuperSheetPopup
import kotlinx.android.synthetic.main.bottom_dialog_register_sessions.view.*
import java.util.*

class RegisterSessionBottomDialog : SuperSheetPopup {
    private var actionSave: ((Sessao) -> Unit)? = null
    private var fragmeent: Fragment = Fragment()
    private var activity: Activity? = null


    constructor(context: Activity, actionSave : ((Sessao) -> Unit)?) : super(context) {
        this.activity = context
        this.actionSave = actionSave
    }

    constructor(context: Fragment) : super(context.requireContext()) {
        this.fragmeent = context
    }

    override fun createView(): View {
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_register_sessions, null)
        v.btn_save.setOnClickListener {
            val nota = v.findViewById<RadioButton>(v.group_nota.checkedRadioButtonId)?.text?.toString()?.toInt()
            val observacao = v.edt_observacao.text.toString()
            nota?.let { avaliation ->
                actionSave?.invoke(Sessao(avaliation, Date(), observacao))
                return@setOnClickListener
            }
            Toast.makeText(context, "Marque alguma avaliação para a sessão", Toast.LENGTH_SHORT).show()
        }

        return v
    }

}