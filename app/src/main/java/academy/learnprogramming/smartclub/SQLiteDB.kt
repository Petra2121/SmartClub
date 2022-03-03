package academy.learnprogramming.smartclub

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import androidx.core.content.contentValuesOf

class SQLiteDB (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private const val DATABASE_VERSION = 1
            private const val DATABASE_NAME = "employee.db"
            private const val TBL_EMPLOYEE = "tbl_employee"
            private const val ID = "id"
            private const val NAME = "name"
            private const val EMAIL = "email"
            private const val DATE = "date"
            private const val ARRIVAL_TIME = "arrival_time"
            private const val END_TIME = "end_time"
            private const val SELECTED_ITEM = "selected_item"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblEmployee = ("CREATE TABLE " + TBL_EMPLOYEE + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT," + DATE + " TEXT,"
                + ARRIVAL_TIME + " TEXT," + END_TIME + " TEXT,"
                + SELECTED_ITEM + " INTEGER" + ")")
        db?.execSQL(createTblEmployee)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_EMPLOYEE")
        onCreate(db)
    }

    fun insertEmployee(std: EmployeeModel): Long {
        val db = this.writableDatabase

        val contentValues = contentValuesOf()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)
        contentValues.put(DATE, std.date)
        contentValues.put(ARRIVAL_TIME, std.arrival_time)
        contentValues.put(END_TIME, std.end_time)
        contentValues.put(SELECTED_ITEM, std.selected_item)


        val success = db.insert(TBL_EMPLOYEE, null, contentValues)
        db.close()


        return success
    }

    fun getAllEmployee(): ArrayList<EmployeeModel> {
        val empList: ArrayList<EmployeeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_EMPLOYEE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor=db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String
        var date: String
        var arrival_time: String
        var end_time: String
        var selected_item: Int

        if(cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                arrival_time = cursor.getString(cursor.getColumnIndexOrThrow("arrival_time"))
                end_time = cursor.getString(cursor.getColumnIndexOrThrow("end_time"))
                selected_item = cursor.getInt(cursor.getColumnIndexOrThrow("selected_item"))

                val emp = EmployeeModel(id = id, name = name, email = email, date = date, arrival_time = arrival_time, end_time = end_time, selected_item = selected_item)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

        return empList
    }

    fun updateEmployeeArrival(arrTime: String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ARRIVAL_TIME, arrTime)

        val success = db.update(TBL_EMPLOYEE, contentValues, "$SELECTED_ITEM=1", null)
        db.close()
        return success
    }

    fun updateEmployeeEnd(endTime: String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(END_TIME, endTime)

        val success = db.update(TBL_EMPLOYEE, contentValues, "$SELECTED_ITEM=1", null)
        db.close()
        return success
    }

    fun updateSelectedItem(selected_item: Int, id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(SELECTED_ITEM, selected_item)

        val success = db.update(TBL_EMPLOYEE, contentValues, "$ID=${id}", null)
        db.close()
        return success
    }



    }
