package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_student)

        val nameEditText = findViewById<EditText>(R.id.edit_text_student_name)
        val idEditText = findViewById<EditText>(R.id.edit_text_student_id)
        val addButton = findViewById<Button>(R.id.button_add)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()

            if (name.isNotBlank() && id.isNotBlank()) {
                val intent = intent
                intent.putExtra("studentName", name)
                intent.putExtra("studentId", id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Please enter valid details!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
