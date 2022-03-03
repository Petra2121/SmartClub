package academy.learnprogramming.smartclub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Welcome : AppCompatActivity() {

    private val arrayList = ArrayList<EmployeeModel>()
    private val displayList = ArrayList<EmployeeModel>()

    private lateinit var sqliteHelper: SQLiteDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        val date = (dateFormat.format(cal.time)).toString()

        sqliteHelper = SQLiteDB(this)


        arrayList.add(EmployeeModel(
            12575,
            "Marko Markov",
            "marko-m@gmail.com",
            date = date,
            "00:00:00",
            "00:00:00",
            0
        ))
        arrayList.add(EmployeeModel(54321, "Tina Peric", "tina-peric@gmail.com", date = date, "00:00:00", "00:00:00", 0))
        arrayList.add(EmployeeModel(12365, "Lana Vanic", "lanav@gmail.com", date = date, "00:00:00", "00:00:00", 0))
        arrayList.add(EmployeeModel(12345, "Tomo Tomic", "tomooo187@gmail.com", date = date, "00:00:00", "00:00:00", 0))
        arrayList.add(EmployeeModel(11177, "Peko Pekic", "pekicp@gmail.com", date = date,"00:00:00", "00:00:00", 0))
        displayList.addAll(arrayList)

        arrayList.forEach {
            val emp = it
            val status = sqliteHelper.insertEmployee(emp)
            if(status >-1) {
                Log.e("Adding", "Successful")
            }else{
                Log.e("Adding", "Not successful")
            }
        }


        val employeeList = sqliteHelper.getAllEmployee()
        Log.e("All employees: ", employeeList.toString())

        val editID = findViewById<TextView>(R.id.editUserID)
        val buttonDone = findViewById<Button>(R.id.done)

        buttonDone.setOnClickListener {
            val editIDNumber = editID.text.toString().toInt()

            for (item in employeeList) {
                if (editIDNumber == item.id) {
                    item.selected_item = 1
                    sqliteHelper.updateSelectedItem(1, item.id)
                    Log.e("Employee:", item.toString())

                    showSuccess()
                    break
                } else {
                    showFail()
                }
            }
        }
    }


    private fun showSuccess() {
        findViewById<EditText>(R.id.editUserID).visibility = View.GONE
        findViewById<TextView>(R.id.useridtxt).visibility = View.GONE
        findViewById<Button>(R.id.done).visibility = View.GONE

        findViewById<ImageButton>(R.id.success).visibility = View.VISIBLE

        val textSuccess: TextView = findViewById(R.id.successTxt)
        textSuccess.visibility = View.VISIBLE
        textSuccess.text = getString(R.string.authentication_success)

        Handler(Looper.getMainLooper()).postDelayed({
            val start = Intent(this, Connecting::class.java)
            startActivity(start)
            finish()
        }, 1500)

    }

    private fun showFail() {
        val textSuccess: TextView = findViewById(R.id.successTxt)
        textSuccess.visibility = View.VISIBLE
        textSuccess.text = getString(R.string.authentication_not_successful)
    }


}