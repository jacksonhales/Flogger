package com.example.flogger.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flogger.R
import com.example.flogger.fragment.ConfirmExitDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        // Set title bar
        setActionBarTitle("Flogger Fitness Logger")

        setBackNavigation()
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    fun setBackNavigation() {
        when(supportActionBar?.title) {
            "Flogger Fitness Logger" -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
            else -> (supportActionBar!!.setDisplayHomeAsUpEnabled(true))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
  /*      ConfirmExitDialogFragment.newInstance(
            getString(R.string.app_name),
            getString(R.string.app_name)
        ).show(supportFragmentManager, ConfirmExitDialogFragment.TAG)*/
        onBackPressed()

        return super.onSupportNavigateUp()
    }
}
