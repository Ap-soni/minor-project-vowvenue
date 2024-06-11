package models.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class sharedviewmodel:ViewModel() {
    val data: MutableLiveData<String> = MutableLiveData()
}