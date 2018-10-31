package com.elieterodrigues.weatherappkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toolbar
import com.elieterodrigues.weatherappkotlin.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.find

class SettingsActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        const val ZIP_CODE = "zipCode"
        const val DEFAULT_ZIP = 32804L
    }

    private var zipCode: Long by DelegatesExt.preference(this, ZIP_CODE, DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initToolbar()
        toolbarTitle = "Settings"
        enableHomeAsUp {
            onBackPressed()
        }

        cityCode.setOnEditorActionListener {
            _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                triggerResearch()
                true
            }
            else {false }
        }

        cityCode.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                triggerResearch()
                true
            } else {
                false
            }

        }
    }

    private fun triggerResearch() {
        if (cityCode.text.isNotEmpty()){
            zipCode = cityCode.text.toString().toLong()
            startActivity<MainActivity>()
        } else {
            toast("Enter a valid zipcode")
        }
    }

}
