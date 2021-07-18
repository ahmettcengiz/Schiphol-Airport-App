package com.example.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flight.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_viewpager_design.view.*


class FlightListViewPagerAdapter(
    private var items: ArrayList<String>,
) : RecyclerView.Adapter<FlightListViewPagerAdapter.MyViewHolder>() {

    var gameList = ArrayList<String>()

    init {
        gameList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_viewpager_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = gameList[position]
        Picasso.get().load(currentItem).into(holder.itemView.imageView)


    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}