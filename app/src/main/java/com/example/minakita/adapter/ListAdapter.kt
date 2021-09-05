package com.example.minakita.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.minakita.*
import com.example.minakita.model.Donasi
import com.example.minakita.uitel.loadImage
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.row_item.view.*

class ListAdapter(var mContext: DonasiFragment, var teacherList:List<Donasi>):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.teacherImageView)
        var nameT = v.findViewById<TextView>(R.id.nameTextView)
        var descriT = v.findViewById<TextView>(R.id.descriptionTextView)
        var target = v.findViewById<TextView>(R.id.targetEditText)
        var hari = v.findViewById<TextView>(R.id.hari)
        var donatur = v.findViewById<TextView>(R.id.donaturTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.row_item,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =teacherList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = teacherList[position]
        holder.nameT.text = newList.namadonasi
        holder.descriT.text = newList.deskripsi
        holder.target.text = newList.target
        holder.imgT.loadImage(newList.imageUrlDonasi)
        holder.v.setOnClickListener {

            val name = newList.namadonasi
            val targett = newList.target
            val descrip  = newList.deskripsi
            val imgUri = newList.imageUrlDonasi
            val harii = newList.batas
            val donaturr = newList.name

            val activity = holder.v.context as Activity
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("NAMET",name)
            intent.putExtra("DESCRIT",descrip)
            intent.putExtra("IMGURI",imgUri)
            intent.putExtra("Target",targett)
            intent.putExtra("hari", harii)
            intent.putExtra("donatur", donaturr)
            mContext.startActivity(intent)

        }
    }
}