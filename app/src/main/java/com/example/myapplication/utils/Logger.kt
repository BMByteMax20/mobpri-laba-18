package com.example.myapplication.utils

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

interface Logger {
    fun log(message: String)
}

@Singleton
class AndroidLogger @Inject constructor() : Logger {
    override fun log(message: String) {
        Log.d("NotesApp", message)
    }
}