package com.example.simpill.ext

import android.app.Activity
import android.content.Context
import android.widget.Button
import com.example.simpill.R

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
    }
}

