package com.example.simpill.ext.treatmentLog

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.simpill.Pill

val DATABASE_NAME = "treatment_log"

class TreatmentLogDatabaseHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, "treatment_log", null, 1)  {

    companion object Factory {
        // Singleton Instance, hacking stuff together for test repo
        lateinit var tldb: TreatmentLogDatabaseHelper
        private var isInitialized: Boolean = false

        open fun getDatabase(context: Context): TreatmentLogDatabaseHelper{
            // TODO: Do I really need this? May just have to call "getWritableDatabase in my methods instead"
            // Hm.. Am doing that though
            if(!isInitialized){
                tldb = TreatmentLogDatabaseHelper(context, null)
                tldb.writableDatabase
                isInitialized = true
            }
            return tldb
        }
    }


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
                c.moveToNext()
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

    fun add(treatment_id: Int, amount: Double?, unit: String?, time: Long) : Boolean{
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

    fun populateWithDummies(pills: Array<Pill>) {
        var reader = getAllEntries()
        if(!reader.moveToFirst()){
            // Only fill if DB is empty
            for(p in pills){
                add(p.primaryKey, 1.5, "hours", java.util.Date().time)
            }
        }
    }
}

data class Treatment(val id: Int, val treatment: String, val amount: Double?, val unit: String?, val time: Int)