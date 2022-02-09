package com.udacity.asteroidradar.main


import android.app.Application

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModelFactory = MainViewModelFactory(Application())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
        binding.viewModel = viewModel
        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.getAsteroidList() //calls the method for getting asteroid list from NASA API
        //Observe the API response and set the list accordingly
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            adapter.data.clear()
            adapter.data.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getImageOfTheDay() //call the method for gettin the pic from NASA API
        //Observe the API response and set the image into the ImageView
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.url).into(binding.activityMainImageOfTheDay)
        })

        viewModel.asteroids.observe(viewLifecycleOwner) {
            viewModel.getAsteroidList()}

            setHasOptionsMenu(true)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImageOfTheDay() //call the method for gettin the pic from NASA API
        //Observe the image and put the API response into the ImageView
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            //Picasso.get().load(it.url).into(binding.activityMainImageOfTheDay)
        })
    }


    //inflate menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Apply filter
        return true
    }
}








