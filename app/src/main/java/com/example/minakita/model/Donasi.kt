package com.example.minakita.model

import com.google.firebase.database.Exclude

data class Donasi(
    var name:String? = null,
    var imageUrlKtp:String? = null,
    var imageUrlDonasi:String? = null,
    var MediaSosial:String? = null,
    var namaInstansi:String? = null,
    var namadonasi:String? = null,
    var target:String? = null,
    var batas:String? = null,
    var penerima:String? = null,
    var deskripsi:String? = null,
    @get:Exclude
    @set:Exclude
    var key:String? = null

)