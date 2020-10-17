package com.example.flogger

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class EditRoutineActivity : AppCompatActivity() {

    private lateinit var editRoutineNameView: EditText
    private lateinit var sentRoutine: Routine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_routine)

        sentRoutine = intent.getParcelableExtra<Routine>("routine")!!

        editRoutineNameView = findViewById(R.id.edit_routine_name)

        val buttonSaveRoutine = findViewById<Button>(R.id.button_save_routine)

        editRoutineNameView.setText(sentRoutine.name)


        buttonSaveRoutine.setOnClickListener{
            val replyIntent = Intent()

            val updatedName = editRoutineNameView.text.toString()

            val updatedRoutine = Routine(sentRoutine.id, updatedName)

            if (TextUtils.isEmpty(editRoutineNameView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else {
                val routineName = editRoutineNameView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, updatedRoutine)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.flogger.editroutine.REPLY"
    }
}