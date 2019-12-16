package br.com.bb.oewallet.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment

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
