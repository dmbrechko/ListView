package com.example.listview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val list: MutableLiveData<MutableList<User>> = MutableLiveData(mutableListOf())

    fun removeItem(index: Int) {
        list.value = list.value!!.apply { removeAt(index) }
    }

    fun addItem(user: User) {
        list.value = list.value!!.apply { add(user) }
    }
}