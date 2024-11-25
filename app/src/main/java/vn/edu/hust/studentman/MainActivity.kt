package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private lateinit var studentListView: ListView
  private lateinit var studentAdapter: ArrayAdapter<String>
  private val students = mutableListOf(
    "Nguyễn Văn An - SV001",
    "Trần Thị Bảo - SV002"
  )
  private var selectedPosition: Int = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentListView = findViewById(R.id.list_view_students)

    // Adapter cho ListView
    studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
    studentListView.adapter = studentAdapter

    // Đăng ký ContextMenu cho ListView
    registerForContextMenu(studentListView)

    // Xử lý sự kiện click item
    studentListView.onItemClickListener =
      AdapterView.OnItemClickListener { _, _, position, _ ->
        Toast.makeText(this, "Selected: ${students[position]}", Toast.LENGTH_SHORT).show()
      }
  }

  // Tạo Menu (OptionMenu)
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.option_menu, menu)
    return true
  }

  // Xử lý click Menu (OptionMenu)
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_new -> {
        // Mở AddActivity để thêm sinh viên mới
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, 1)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  // Tạo ContextMenu cho ListView
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)

    val info = menuInfo as AdapterView.AdapterContextMenuInfo
    selectedPosition = info.position
  }

  // Xử lý click ContextMenu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_edit -> {
        // Mở EditActivity để chỉnh sửa thông tin
        val intent = Intent(this, EditStudentActivity::class.java).apply {
          putExtra("studentData", students[selectedPosition])
          putExtra("position", selectedPosition)
        }
        startActivityForResult(intent, 2)
      }
      R.id.menu_remove -> {
        // Xóa sinh viên khỏi danh sách
        students.removeAt(selectedPosition)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Student removed!", Toast.LENGTH_SHORT).show()
      }
    }
    return super.onContextItemSelected(item)
  }

  // Nhận kết quả từ các Activity
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      val studentName = data.getStringExtra("studentName")
      val studentId = data.getStringExtra("studentId")
      val position = data.getIntExtra("position", -1)

      if (requestCode == 1 && studentName != null && studentId != null) {
        // Thêm sinh viên mới
        students.add("$studentName - $studentId")
        studentAdapter.notifyDataSetChanged()
      } else if (requestCode == 2 && studentName != null && studentId != null) {
        // Cập nhật sinh viên
        students[position] = "$studentName - $studentId"
        studentAdapter.notifyDataSetChanged()
      }
    }
  }
}
