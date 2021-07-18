package com.example.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flight.R
import com.example.local.ReservationViewModel
import com.example.model.ResEntity
import com.example.view.adapters.ReservationListRecyclerAdapter
import kotlinx.android.synthetic.main.activity_reservation.*

class ReservationActivity : AppCompatActivity() {
    private var adapter: ReservationListRecyclerAdapter? = null
    private lateinit var reservationViewModel: ReservationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        reservationViewModel = ViewModelProvider(this)[ReservationViewModel::class.java]
        fetchFavorites()
        bottomNavBar()



    }

    private fun fetchFavorites() {

        reservationViewModel.allReservation.observe(this, Observer { list ->
            adapter = ReservationListRecyclerAdapter(
                list,
                object : ReservationListRecyclerAdapter.OnClickListener {

                    override fun onItemClick(position: ResEntity) {
                        val intent = Intent(this@ReservationActivity, SeatActivity::class.java)
                        intent.putExtra("page", "2")
                        intent.putExtra("key", position.seat)
                        intent.putExtra("id", position.id)
                        intent.putExtra("iata", position.iata)
                        startActivity(intent)

                    }

                })

            recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerview.adapter = adapter


        })
    }
    private fun bottomNavBar() {
        bottom_navigation_bar.selectedItemId = R.id.navigation_homePage
        bottom_navigation_bar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_favorites -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_homePage -> {
                    val intent = Intent(this@ReservationActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ReservationActivity, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}