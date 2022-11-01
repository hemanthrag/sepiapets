package com.sepia.pets.ui

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import java.nio.charset.Charset

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}