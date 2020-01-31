package br.com.fisioapp.ui.components

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView


public class FatherScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : StickyScrollView(context, attrs, defStyleAttr) {
    var listRecyclersViews: ArrayList<RecyclerView> = ArrayList()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findRecyclers(getChildAt(0))
            listRecyclersViews.forEach {
                it.isNestedScrollingEnabled = false
            }
            setOnScrollChangeListener { view, x, y, oldx, oldy ->
                val tt = getChildAt(childCount - 1)
                val scrolled = (tt.bottom - (height + scrollY))
                listRecyclersViews.forEach {
                    it.isNestedScrollingEnabled = (scrolled == 0 )
                }
            }
        }
    }

    private fun findRecyclers(v: View?) {
        if (v is ViewGroup) {
            for (i in 0 until v.childCount) {
                val it = v.getChildAt(i)
                if (it is RecyclerView) {
                    listRecyclersViews.add(it)
                }else{
                    findRecyclers(it)
                }
            }
        }
    }

}
