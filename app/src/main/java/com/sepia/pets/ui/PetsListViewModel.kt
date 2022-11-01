package com.sepia.pets.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sepia.pets.models.AllPets
import com.sepia.pets.models.WorkingHours
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("StaticFieldLeak")
class PetsListViewModel(application: Application):AndroidViewModel(application) {

    var petsListListener:PetsListListener? = null
    val context = getApplication<Application>().applicationContext
    val petsListData = MutableLiveData<AllPets>()
    val accessOrNotMutableLiveData = MutableLiveData<Boolean>()
    var accessOrNot:Boolean?=null

    fun getJsonData() {
        petsListListener!!.onStarted()
        try {
            val inputSystem = context.assets.open("pets_list.json")
            val size = inputSystem.available()
            val byteArray = ByteArray(size)
            inputSystem.read(byteArray)
            inputSystem.close()
            val allPets: AllPets = Gson().fromJson(String(byteArray, Charset.forName("UTF-8")), AllPets::class.java)
            petsListData.value = allPets
            petsListListener!!.onSuccess(petsListData)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorkingHoursData(){
        try {
            val inputSystem = context.assets.open("config.json")
            val size = inputSystem.available()
            val byteArray = ByteArray(size)
            inputSystem.read(byteArray)
            inputSystem.close()
            val workingHours: WorkingHours = Gson().fromJson(String(byteArray, Charset.forName("UTF-8")), WorkingHours::class.java)
            var weekAndTime = workingHours.settings!!.workHours!!.split(" ")

            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_WEEK]

            var startDate:Int?=getStarEndDay(weekAndTime[0].split("-")).split(" ")[0].toInt()
            var endDate:Int?=getStarEndDay(weekAndTime[0].split("-")).split(" ")[1].toInt()
            getStarEndDay(weekAndTime[0].split("-"))
            for(i in endDate?.let { startDate?.rangeTo(it) }!!){
                if (day == i){
                    accessOrNot = true
                    break
                }
            }

            var currenttime =  DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now())
            var startTime = weekAndTime[1].split("-")[0]
            var endTime = weekAndTime[1].split("-")[1]

            val formatter = SimpleDateFormat("HH:mm")
            val date_from: Date? = formatter.parse(startTime)
            val date_to: Date? = formatter.parse(endTime)
            val dateNow: Date? = formatter.parse(currenttime)
            if (date_from!!.before(dateNow) && date_to!!.after(dateNow)) {
                accessOrNotMutableLiveData.value = accessOrNot!=null
            }else{
                accessOrNotMutableLiveData.value = false
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getStarEndDay(startEndDayList:List<String>):String {
        var startDate:Int?=null
        var endDate:Int?=null
        for(startEndDay in startEndDayList){
            when (startEndDay) {
                "Sun"->{
                    if(startDate==null) startDate = Calendar.SUNDAY else endDate = Calendar.SUNDAY
                }
                "Mon"->{
                    if(startDate==null) startDate = Calendar.MONDAY else endDate = Calendar.MONDAY
                }
                "Tue"->{
                    if(startDate==null) startDate = Calendar.TUESDAY else endDate = Calendar.TUESDAY
                }
                "Wed"->{
                    if(startDate==null) startDate = Calendar.WEDNESDAY else endDate = Calendar.WEDNESDAY
                }
                "Thu"->{
                    if(startDate==null) startDate = Calendar.THURSDAY else endDate = Calendar.THURSDAY
                }
                "Fri"->{
                    if(startDate==null) startDate = Calendar.FRIDAY else endDate = Calendar.FRIDAY
                }
                "Sat"->{
                    if(startDate==null) startDate = Calendar.SATURDAY else endDate = Calendar.SATURDAY
                }
            }
        }
        return "$startDate $endDate"
    }


}
