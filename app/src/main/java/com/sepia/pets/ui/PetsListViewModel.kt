package com.sepia.pets.ui

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sepia.pets.models.AllPets
import java.nio.charset.Charset

@SuppressLint("StaticFieldLeak")
class PetsListViewModel(application: Application):AndroidViewModel(application) {

    var petsListListener:PetsListListener? = null
    val context = getApplication<Application>().applicationContext
    val petsListData = MutableLiveData<AllPets>()


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


}
