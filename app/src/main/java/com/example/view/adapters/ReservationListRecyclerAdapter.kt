package com.example.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flight.R
import com.example.model.ResEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row_design.view.*

class ReservationListRecyclerAdapter(
    private var items: List<ResEntity>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<ReservationListRecyclerAdapter.MyViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: ResEntity)
    }

    var flightList = emptyList<ResEntity>()

    init {
        flightList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return flightList.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = flightList[position]
        holder.itemView.nameOfFlight.text = currentItem.flightName
        Picasso.get().load(R.drawable.ic_arrivals).into(holder.itemView.typeImageView)
        if (currentItem.flightDirection == "D") {
            holder.itemView.Destination.text = "AAS - " + currentItem.iata
            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_departures)
                .into(holder.itemView.typeImageView)
        } else {
            holder.itemView.Destination.text = currentItem.iata + " - AAS"
            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_arrivals)
                .into(holder.itemView.typeImageView)
        }

        holder.itemView.date.text = currentItem.scheduleDateTime
        holder.itemView.rowLayout.setOnClickListener {
            onClickListener.onItemClick(currentItem)

        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}