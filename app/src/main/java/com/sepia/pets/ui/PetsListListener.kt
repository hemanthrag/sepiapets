package com.sepia.pets.ui

import androidx.lifecycle.LiveData
import com.sepia.pets.models.AllPets

interface PetsListListener {
    fun onStarted()
    fun onSuccess(petsListData:LiveData<AllPets>)
}