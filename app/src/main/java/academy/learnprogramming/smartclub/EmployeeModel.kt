package academy.learnprogramming.smartclub

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class EmployeeModel (
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = "",
    var date: String = getDate(),
    var arrival_time: String = "00:00:00",
    var end_time: String = "00:00:00",
    var selected_item: Int = 0
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt( 99999)
        }
        fun getDate(): String {
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val cal = Calendar.getInstance()
            return (dateFormat.format(cal.time)).toString()
        }
    }

}