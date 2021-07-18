package com.example.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flight.R
import com.example.model.FlightModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row_design.view.*

class FlightListRecyclerViewAdapter(
    private var items: List<FlightModel>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<FlightListRecyclerViewAdapter.MyViewHolder>(), Filterable {

    interface OnClickListener {
        fun onItemClick(position: FlightModel)
    }

    var flightList : List<FlightModel> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return flightList.size;
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = flightList[position]
        holder.itemView.nameOfFlight.text = currentItem.flightName
        Picasso.get().load(R.drawable.ic_arrivals).into(holder.itemView.typeImageView)
        if(currentItem.flightDirection=="D"){
            holder.itemView.Destination.text = "AAS - " + currentItem.route.destinations.last()
            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_departures)
                .into(holder.itemView.typeImageView)
        }else{
            holder.itemView.Destination.text = currentItem.route.destinations.first() + " - AAS"
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
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    flightList = items
                } else {
                    val resultList = ArrayList<FlightModel>()
                    for (row in items) {
                        if (row.route.destinations.last().lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    flightList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = flightList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    flightList = results.values as ArrayList<FlightModel>
                    notifyDataSetChanged()
                }

            }

        }
    }
}