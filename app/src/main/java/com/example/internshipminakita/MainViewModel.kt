package com.example.internshipminakita

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    val authState = FirebaseUserLiveData()
}