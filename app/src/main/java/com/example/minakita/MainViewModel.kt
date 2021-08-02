package com.example.minakita

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    val authState = FirebaseUserLiveData()
}