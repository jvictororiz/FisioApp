package br.com.androidstartermvvm.util.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import br.com.androidstartermvvm.R
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton


const val ANIM_DURATION_LONG = 500L

const val ANIM_DURATION_MEDIUM = 250L

private val interpolator = LinearInterpolator()

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun View.show(duration: Long = 200, onAnimationEnd: (() -> Unit?)? = null) {
    this.animate()
        .alpha(1.0f)
        .setDuration(duration)
        .also { animator ->
            if (onAnimationEnd != null) {
                animator.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        onAnimationEnd()
                    }
                })

            }
        }
}

fun View.hide(duration: Long = 200, onAnimationEnd: (() -> Unit)? = null) {
    this.animate()
        .alpha(0.0f)
        .setDuration(duration)
        .also { animator ->
            if (onAnimationEnd != null) {
                animator.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        onAnimationEnd()
                    }
                })

            }
        }
}

fun CircularProgressButton.showLoad() {
    startAnimation()
}

fun CircularProgressButton.hideLoad() {
    revertAnimation()
}

fun CircularProgressButton.successLoad(color: Int = R.color.white) {
    (R.drawable.ic_check_white).getBitmapFromVectorDrawable(context)?.let {
        doneLoadingAnimation(
        color, it
        )
    }
}
