package com.example.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flight.R
import com.example.model.*
import com.example.view.adapters.FlightListRecyclerViewAdapter
import com.example.view.adapters.FlightListViewPagerAdapter
import com.example.viewmodel.FlightListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapterRecyclerView: FlightListRecyclerViewAdapter? = null
    private var adapterViewPager: FlightListViewPagerAdapter? = null
    private lateinit var flightListViewModel: FlightListViewModel
    private var list= arrayListOf<FlightModel>()
    private var ListViewPager: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flightListViewModel = ViewModelProvider(this)[FlightListViewModel::class.java]
        for(i in 1..2){
            flightListViewModel.getListOfFlights(i)
        }
        observeModelData()
        setViewPagerAdapter()
        bottomNavBar()

    }
    private fun initRecycler() {
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = adapterRecyclerView
    }
    private fun observeModelData() {
        flightListViewModel.listOfFlight.observe(this, Observer {

                list = it!!.flights as ArrayList<FlightModel>
                for (i in list){
                    Log.e("kaka", i.route.destinations.last())

                }

            setRecylcerViewAdapter()
            initRecycler()
            showEmptyError()
            searchFilter()

        })

        flightListViewModel.errorLD.observe(this, Observer {
            println("ERROR: ${it.toString()}")
        })


    }
    private fun setRecylcerViewAdapter() {
        adapterRecyclerView = FlightListRecyclerViewAdapter(
            list,
            object : FlightListRecyclerViewAdapter.OnClickListener {
                override fun onItemClick(position: FlightModel) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("id", position.id)
                    intent.putExtra("iata", position.route.destinations.last())
                    startActivity(intent)

                }

            })
    }

    private fun setViewPagerAdapter() {
        ListViewPager.add("https://i.ytimg.com/vi/6vvaXwuRcN0/maxresdefault.jpg")
        ListViewPager.add("https://factualtrip.com/wp-content/uploads/2019/04/flights.jpg")
        ListViewPager.add("https://www.austrian.com/content/dam/austrian/global/images/footer-pages/company/company-11x4-contentheader.jpg.transform/lh-dcep-transform-width-1440/img.jpg")
        adapterViewPager = FlightListViewPagerAdapter(ListViewPager)
        viewPager.adapter = adapterViewPager
        worm_dots_indicator.setViewPager2(viewPager)
    }
    private fun bottomNavBar() {
        bottom_navigation_bar.selectedItemId = R.id.navigation_homePage
        bottom_navigation_bar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_homePage -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this@MainActivity, ReservationActivity::class.java)
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
    private fun searchFilter() {
        game_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 2) {
                    adapterRecyclerView!!.filter.filter(newText)
                    viewPager.visibility = View.GONE
                    worm_dots_indicator.visibility = View.GONE
                } else if (newText.isEmpty()) {
                    adapterRecyclerView!!.filter.filter(newText)
                    viewPager.visibility = View.VISIBLE
                    worm_dots_indicator.visibility = View.VISIBLE

                }

                return false
            }

        })
    }
    private fun showEmptyError() {
        adapterRecyclerView!!.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                empty.visibility =
                    (if (adapterRecyclerView!!.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
        recyclerview.adapter = adapterRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

}