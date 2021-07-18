package com.example.view.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.flight.R
import com.example.viewmodel.FlightListViewModel
import androidx.lifecycle.Observer
import com.example.local.ReservationViewModel
import com.example.model.ResEntity
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import kotlinx.android.synthetic.main.activity_seat.*

class SeatActivity : AppCompatActivity() {
    private lateinit var flightListViewModel: FlightListViewModel
    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var temp: String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)
        flightListViewModel = ViewModelProvider(this)[FlightListViewModel::class.java]
        reservationViewModel = ViewModelProvider(this)[ReservationViewModel::class.java]
        val page = intent.getStringExtra("page")
        if (page=="2"){
            button.visibility= View.GONE
        }else{
            button.visibility= View.VISIBLE
        }
        val numberList = intent.getSerializableExtra("key")
        val id = intent.getStringExtra("id")
        val iata = intent.getStringExtra("iata")
        if (id != null) {
            flightListViewModel.getFlightByID(id)
        }
        if (iata != null) {
            flightListViewModel.getDestination(iata)
        }
        observeModelData()
        observeModelDest()
        textView9.text=numberList.toString()

        button.setOnClickListener {
            val temp = ResEntity(id!!,numberList.toString(),iata!!,textView3.text.toString(),temp,textView7.text.toString())
            reservationViewModel.insertReservation(temp)
            val intent = Intent(this@SeatActivity, ReservationActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeModelData() {
        flightListViewModel.flightByID.observe(this, Observer {
            if (it != null) {
                temp = it.flightDirection
                textView3.text=it.flightName
                textView7.text=it.scheduleDateTime
                displayBitmap(it.flightName)
            }
        })

    }
    private fun observeModelDest() {
        flightListViewModel.destination.observe(this, Observer {
            if (it != null) {
                textView5.text=it.publicName.english

            }
        })

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun displayBitmap(value: String) {
        val widthPixels = resources.getDimensionPixelSize(R.dimen.width_barcode)
        val heightPixels = resources.getDimensionPixelSize(R.dimen.height_barcode)

        imageView2.setImageBitmap(
            createBarcodeBitmap(
                barcodeValue = value,
                barcodeColor = getColor(R.color.black),
                backgroundColor = getColor(android.R.color.white),
                widthPixels = widthPixels,
                heightPixels = heightPixels
            )
        )

    }

    private fun createBarcodeBitmap(
        barcodeValue: String,
        @ColorInt barcodeColor: Int,
        @ColorInt backgroundColor: Int,
        widthPixels: Int,
        heightPixels: Int
    ): Bitmap {
        val bitMatrix = Code128Writer().encode(
            barcodeValue,
            BarcodeFormat.CODE_128,
            widthPixels,
            heightPixels
        )

        val pixels = IntArray(bitMatrix.width * bitMatrix.height)
        for (y in 0 until bitMatrix.height) {
            val offset = y * bitMatrix.width
            for (x in 0 until bitMatrix.width) {
                pixels[offset + x] =
                    if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
            }
        }

        val bitmap = Bitmap.createBitmap(
            bitMatrix.width,
            bitMatrix.height,
            Bitmap.Config.ARGB_8888
        )
        bitmap.setPixels(
            pixels,
            0,
            bitMatrix.width,
            0,
            0,
            bitMatrix.width,
            bitMatrix.height
        )
        return bitmap
    }
}