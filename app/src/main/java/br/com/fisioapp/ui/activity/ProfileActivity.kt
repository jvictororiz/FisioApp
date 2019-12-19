package br.com.fisioapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fisioapp.R
import br.com.fisioapp.ui.base.BaseActivity

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}
