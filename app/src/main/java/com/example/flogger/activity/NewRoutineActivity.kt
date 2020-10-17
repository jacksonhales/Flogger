package com.example.flogger.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.flogger.R

class NewRoutineActivity : AppCompatActivity() {

    private lateinit var editRoutineNameView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_routine)

        editRoutineNameView = findViewById(R.id.edit_routine_name)

        val buttonSaveRoutine = findViewById<Button>(R.id.button_save_routine)

        buttonSaveRoutine.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editRoutineNameView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val routineName = editRoutineNameView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, routineName)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.flogger.newroutine.REPLY"
    }
}