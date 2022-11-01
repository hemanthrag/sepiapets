package com.sepia.pets.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sepia.pets.R
import com.sepia.pets.adapter.PetsListAdapter
import com.sepia.pets.databinding.PetsListActivityBinding
import com.sepia.pets.models.AllPets
import java.nio.charset.Charset

class PetsListActivity : AppCompatActivity(),PetsListListener {
    var binding:PetsListActivityBinding? = null
    var viewmodel:PetsListViewModel? = null
    var petsListAdapter: PetsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.pets_list_activity)
        viewmodel = ViewModelProviders.of(this).get(PetsListViewModel::class.java)
        binding!!.recyclerview.layoutManager = LinearLayoutManager(this)
        binding!!.recyclerview.setHasFixedSize(true)
        viewmodel!!.petsListListener = this
        petsListAdapter = PetsListAdapter();
        binding!!.recyclerview.adapter = petsListAdapter
        viewmodel!!.getJsonData()
    }

    override fun onStarted() {
        binding!!.progressBar.show()
    }

    override fun onSuccess(petsListData: LiveData<AllPets>) {
        petsListData.observe(this, Observer {
            petsListAdapter?.setAllPets(it as AllPets)
            binding!!.progressBar.hide()
        })
    }
}