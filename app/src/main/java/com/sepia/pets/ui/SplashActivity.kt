package com.sepia.pets.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.sepia.pets.R
import java.time.Clock
import java.time.LocalTime
import java.util.*

class SplashActivity:AppCompatActivity() {
    var viewmodel:PetsListViewModel? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewmodel = ViewModelProviders.of(this).get(PetsListViewModel::class.java)

        viewmodel!!.getWorkingHoursData()

        viewmodel!!.accessOrNotMutableLiveData.observe(this){
            if(it){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, PetsListActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.app_name)
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                builder.setPositiveButton("Ok"){dialogInterface, which ->
                    finish()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }
}