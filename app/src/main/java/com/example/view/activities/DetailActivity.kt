package com.example.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flight.R
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() , View.OnClickListener {
    var layout: ViewGroup? = null

    var seats = (
              "________/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "________/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "AAA__AAAA/"
            + "________/")

    var seatViewList: ArrayList<TextView> = ArrayList()
    var df: ArrayList<Int> = ArrayList()
    var seatSize = 100
    var seatGaping = 5

    var STATUS_AVAILABLE = 1
    var STATUS_RESERVED = 2
    var selectedIds = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent .getStringExtra("id")
        val iata = intent .getStringExtra("iata")
        layout = findViewById(R.id.layoutSeat)

        seats = "/$seats"

        val layoutSeat = LinearLayout(this)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutSeat.orientation = LinearLayout.VERTICAL
        layoutSeat.layoutParams = params
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping)
        layout?.addView(layoutSeat)

        var layout: LinearLayout? = null

        var count = 0

        for (index in 0 until seats.length) {
            if (seats[index] == '/') {
                layout = LinearLayout(this)
                layout.orientation = LinearLayout.HORIZONTAL
                layoutSeat.addView(layout)
            } else if (seats[index] == 'A') {
                count++
                val view = TextView(this)
                val layoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.id = count
                view.gravity = Gravity.CENTER
                view.setBackgroundResource(R.drawable.ic_baseline_event_seat_24)
                view.text = count.toString() + ""
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                view.setTextColor(Color.BLACK)
                view.tag = STATUS_AVAILABLE
                layout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            } else if (seats[index] == 'R') {
                count++
                val view = TextView(this)
                val layoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setPadding(0, 0, 0, 2 * seatGaping)
                view.id = count
                view.gravity = Gravity.CENTER
                view.setBackgroundResource(R.drawable.ic_baseline_airline_seat_recline_normal_24)
                view.text = count.toString() + ""
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9f)
                view.setTextColor(Color.WHITE)
                view.tag = STATUS_RESERVED
                layout!!.addView(view)
                seatViewList.add(view)
                view.setOnClickListener(this)
            } else if (seats[index] == '_') {
                val view = TextView(this)
                val layoutParams = LinearLayout.LayoutParams(seatSize, seatSize)
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping)
                view.layoutParams = layoutParams
                view.setBackgroundColor(Color.TRANSPARENT)
                view.text = ""
                layout!!.addView(view)
            }
        }



        asd.setOnClickListener {
            val intent = Intent(this@DetailActivity, SeatActivity::class.java)
            intent.putExtra("page", "1")
            intent.putExtra("key", df)
            intent.putExtra("id", id)
            intent.putExtra("iata", iata)
            startActivity(intent)
        }
    }

    override fun onClick(view: View) {
        if (view.getTag() as Int == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId().toString() + ",")) {
                selectedIds = selectedIds.replace(view.getId().toString() + ",", "")
                df.remove(view.id)
                view.setBackgroundResource(R.drawable.ic_baseline_event_seat_24)
            } else {
                selectedIds = selectedIds + view.getId().toString() + ","
                df.add(view.id)
                view.setBackgroundResource(R.drawable.ic_baseline_airline_seat_recline_normal_24)
            }
        }else if (view.getTag() as Int == STATUS_RESERVED) {
            Toast.makeText(
                this,
                "Seat " + view.getId().toString() + " is Reserved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}