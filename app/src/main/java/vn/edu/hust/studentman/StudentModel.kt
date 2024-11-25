package vn.edu.hust.studentman

data class StudentModel(val studentName: String, val studentId: String) {
    override fun toString(): String {
        return "$studentName - $studentId"
    }
}