package com.example.task53

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView

class Adapter(private val data: ArrayList<Cat>): RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)!!
        val temperament = view.findViewById<TextView>(R.id.temperament)!!
        val image = view.findViewById<ImageView>(R.id.image)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].breeds[0].name
        holder.temperament.text = data[position].breeds[0].temperament
        val uri: Uri = Uri.parse(data[position].url)
        holder.image.setImageURI(uri)
    }

    override fun getItemCount(): Int = data.size
}