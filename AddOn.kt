package com.example.simpill.ext

import android.app.Activity
import android.widget.Button
import com.example.simpill.DatabaseHelper
import com.example.simpill.treatmentLog.TreatmentLogDatabase

/**
 * To feed into python script, add around every function that is called from external repository.
 */

val MARKER_START = "IXwDmcyAEZEUvkES0IXy144JB SimPillAddOn start"
val MARKER_END = "IXwDmcyAEZEUvkES0IXy144JB SimPillAddOn end"


open class AddOn {
    companion object {
        @JvmStatic fun initializeGoToLogButton(button: Button, activity: Activity) : Unit {
            button.setOnClickListener { openTreatmentLogActivity() };

        }

        @JvmStatic fun openTreatmentLogActivity() : Unit {

        }


        @JvmStatic fun populateDatabases(tdb: TreatmentLogDatabase, pillDb: DatabaseHelper): Unit {
            pillDb.populateWithDummies()
            tdb.populateWithDummies(pillDb.allPills)
        }

        // Random comment for testing purposes
    }
}

