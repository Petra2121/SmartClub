package academy.learnprogramming.smartclub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_connecting.*

class Connecting : AppCompatActivity() {

    private lateinit var sqliteHelper: SQLiteDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connecting)

        sqliteHelper = SQLiteDB(this)

        val employeeList = sqliteHelper.getAllEmployee()
        Log.e("Number of employees: ", "${employeeList.size}")


        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->

            if (isConnected) {

                val timeStart = timeStart()

                employeeList.forEach {
                    if (it.selected_item == 1) {
                        it.arrival_time = timeStart
                        sqliteHelper.updateEmployeeArrival(timeStart)
                        Log.e("Working time started", it.toString())
                    }
                }

                disconnectedLayout.visibility = View.GONE
                connectingLayout.visibility = View.GONE
                connectedLayout.visibility = View.VISIBLE

            } else {

                disconnectedLayout.visibility = View.VISIBLE

                val timeEnd = timeEnd()

                employeeList.forEach {
                    if (it.selected_item == 1) {
                        it.end_time = timeEnd

                        sqliteHelper.updateEmployeeEnd(timeEnd)
                        sqliteHelper.updateSelectedItem(0, it.id)
                        Log.e("Working time ended for", it.toString())
                    }
                }

                connectingLayout.visibility = View.GONE
                connectedLayout.visibility = View.GONE

                Handler(Looper.getMainLooper()).postDelayed({
                    val start = Intent(this, MainActivity::class.java)
                    startActivity(start)
                    finish()
                }, 4500)


            }

        }

    }

    private fun timeStart() : String {

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getTimeInstance()
        val formattedTime = formatter.format(date)

        val timeStart = findViewById<TextView>(R.id.timeStart)
        timeStart.text = formattedTime

        return formattedTime
    }

    private fun timeEnd() : String {

        val dateEnd = Calendar.getInstance().time
        val formatterEnd = SimpleDateFormat.getTimeInstance()
        val formattedTimeEnd = formatterEnd.format(dateEnd)

        val timeEnd = findViewById<TextView>(R.id.timeEnd)
        timeEnd.text = formattedTimeEnd

        return formattedTimeEnd
    }

}