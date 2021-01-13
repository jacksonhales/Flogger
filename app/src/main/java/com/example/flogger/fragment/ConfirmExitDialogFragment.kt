package com.example.flogger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.flogger.R
import kotlin.system.exitProcess

class ConfirmExitDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String, subTitle: String): ConfirmExitDialogFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = ConfirmExitDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_exit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        val textViewConfirmExitTitle = view?.findViewById<TextView>(R.id.textview_confirm_exit_title)
        val textViewConfirmExitSubtitle = view?.findViewById<TextView>(R.id.textview_confirm_exit_subtitle)
        val buttonViewConfirmExitCancel = view?.findViewById<Button>(R.id.button_confirm_exit_cancel)
        val buttonViewConfirmExitExit = view?.findViewById<Button>(R.id.button_confirm_exit_exit)

        textViewConfirmExitTitle?.text = arguments?.getString(KEY_TITLE)
        textViewConfirmExitSubtitle?.text = arguments?.getString(KEY_SUBTITLE)

        buttonViewConfirmExitCancel?.setOnClickListener {
            dismiss()
        }

        buttonViewConfirmExitExit?.setOnClickListener {
            exitProcess(0)
        }
    }
}