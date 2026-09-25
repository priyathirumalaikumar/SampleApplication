package com.priya.sampleapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,"Student DB",null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = """CREATE TABLE students(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INT)""".trimIndent()
        db.execSQL(query)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        p1: Int,
        p2: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }
    fun insertStudent(name: String,age: Int): Boolean{
        val db = writableDatabase
        val values = ContentValues()
        Log.i("isCheVal","vale--->1")
        values.put("name",name)
        values.put("age",age)
        val result = db.insert("students",null,values)
        return result != -1L
    }

    // SELECT
    fun getStudents(): Cursor {

        val db = readableDatabase

        return db.rawQuery(
            "SELECT * FROM students",
            null
        )
    }
}