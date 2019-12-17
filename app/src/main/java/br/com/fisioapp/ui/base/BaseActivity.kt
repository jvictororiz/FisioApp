package br.com.fisioapp.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.fisioapp.R
import br.com.fisioapp.ui.activity.LoginActivity
import br.com.bb.oewallet.ui.BaseFragment

abstract class BaseActivity : AppCompatActivity() {
    private val unauthorizedFilter by lazy {
        IntentFilter(ACTION_ERROR_UNAUTHORIZED)
    }
    private val unauthorizedReceiver = object : BroadcastReceiver() {
        private fun startLoginActivity(ctx: Context) {
            startActivity(Intent(ctx, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context, intent: Intent?) {
            AlertDialog.Builder(this@BaseActivity, android.R.style.Theme_Material_Light_Dialog_Alert)
                .setTitle(R.string.sessao_expirada)
                .setMessage(R.string.msg_sessao_expirada)
                .setPositiveButton(android.R.string.ok) { _, _ -> startLoginActivity(context) }
                .setOnCancelListener { startLoginActivity(this@BaseActivity) }
                .show()
        }

    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(unauthorizedReceiver)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            unauthorizedReceiver, unauthorizedFilter)
    }



    fun replace(fragment: BaseFragment, addToBackstack: Boolean? = false) {
        val fg = supportFragmentManager.findFragmentByTag(fragment.fragmentTag) ?: fragment
        supportFragmentManager.beginTransaction()
            .also {
                if (addToBackstack == true) {
                    it.addToBackStack(fragment.fragmentTag)
                }

            }
            .replace(R.id.container, fg, fragment.fragmentTag)
            .commit()
    }

    fun startActivityAnim(intent:Intent){
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }

    companion object{
        const val  ACTION_ERROR_UNAUTHORIZED = "ACTION_ERROR_UNAUTHORIZED"
    }

}
