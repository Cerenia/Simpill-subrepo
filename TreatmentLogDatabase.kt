package com.example.simpill.ext

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

val DATABASE_NAME = "treatment_log"

class TreatmentLogDatabase(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, "treatment_log", factory, 0)  {

    val TABLE_NAME = DATABASE_NAME + "_table"

    /**
     * Primary key
     */
    val ID_COL = "id"

    /**
     * References primary key of @see DatabaseHelper
     */
    val TREATMENT_COL = "treatment"

    /**
     * How many pills, minutes, mg...
     */
    val AMOUNT_COL = "amount"
    val UNIT_COL = "unit"

    /**
     * unix timestamp
     */
    val TIME_COL = "time"

    open inner class Reader(){
        val list = ArrayList<Treatment>()
        var idx = 0

        fun fill(c: Cursor): Reader {
            c.moveToFirst()
            while(!c.isAfterLast){
                val id = c.getInt(c.getColumnIndexOrThrow(ID_COL))
                val treatment = c.getString(c.getColumnIndexOrThrow(TREATMENT_COL))
                val amountIdx = c.getColumnIndex(AMOUNT_COL)
                val amount = when(amountIdx){
                    -1 -> null
                    else -> c.getDouble(amountIdx)
                }
                val unitIdx = c.getColumnIndex(UNIT_COL)
                val unit = when(unitIdx){
                    -1 -> null
                    else -> c.getString(unitIdx)
                }
                val time = c.getInt(c.getColumnIndexOrThrow(TIME_COL))
                list.add(Treatment(id, treatment, amount, unit, time))
            }
            idx = list.size // set the index after the last element to start
            return this
        }

        /**
         * True for a new Reader unless move to first is called.
         */
        open fun isAfterLast() : Boolean{
            return idx >= list.size
        }

        /**
         * False if the list is empty.
         */
        open fun moveToFirst() : Boolean{
            idx = 0
            return list.size > 0
        }

        /**
         * Get current element and advance to the next one.
         */
        open fun getNext() : Treatment {
            return list.get(idx++)
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TREATMENT_COL
                + " INTEGER NOT NULL, "
                + AMOUNT_COL
                + " REAL, "
                + UNIT_COL
                + " TEXT, "
                + TIME_COL
                + " INTEGER NOT NULL);")
        db!!.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun add(treatment_id: Int, amount: Double?, unit: String?, time: Int) : Boolean{
        val contentValues = contentValuesOf(Pair(TREATMENT_COL, treatment_id),
                                            Pair(AMOUNT_COL, amount),
                                            Pair(UNIT_COL, unit),
                                            Pair(TIME_COL, time))
        val result = this.writableDatabase.insert(TABLE_NAME, "NULL", contentValues)
        return result != -1L
    }

    fun delete(id: Int) : Boolean{
        val nrDeletedRows = this.writableDatabase.delete(TABLE_NAME, "$ID_COL = ?", arrayOf(id.toString()))
        return nrDeletedRows == 1
    }

    fun getAllEntries() : Reader {
        val cursor = this.readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
        return Reader().fill(cursor)
    }
}

data class Treatment(val id: Int, val treatment: String, val amount: Double?, val unit: String?, val time: Int)