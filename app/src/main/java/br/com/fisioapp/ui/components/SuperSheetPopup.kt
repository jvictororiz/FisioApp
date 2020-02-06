package br.com.fisioapp.ui.components

import android.content.Context
import android.view.View
import br.com.fisioapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by eduardo.sampaio on 06/11/17.
 */
abstract class SuperSheetPopup(protected var context: Context) {
    protected var mBottomSheetDialog: BottomSheetDialog? = null
    private var view: View? = null
    var isShowing = false
        private set

   open fun show() {
        isShowing = true
        if (view == null) {
            mBottomSheetDialog = BottomSheetDialog(context, R.style.SheetDialog)
            view = createView()
            mBottomSheetDialog!!.setContentView(view!!)
        }
        mBottomSheetDialog!!.show()
    }

    fun resetView(view: View?) {
        if (view != null) {
            mBottomSheetDialog = BottomSheetDialog(context)
            mBottomSheetDialog!!.setContentView(view)
        }
    }

    fun dismiss() {
        isShowing = false
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog!!.dismiss()
        }
    }

    protected abstract fun createView(): View?

}