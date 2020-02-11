package com.example.justlikett

import android.app.Activity
import android.content.Context

class SharedPreferenceUtil(
    context:Context
) {
    var PREF_NAME = "myData.SharedPreference"
    init {
        var pref = context.getSharedPreferences("myData", Activity.MODE_PRIVATE)
    }
}