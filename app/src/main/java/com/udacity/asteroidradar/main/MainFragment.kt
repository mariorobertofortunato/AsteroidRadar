package com.udacity.asteroidradar.main


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val viewModelFactory = MainViewModelFactory(requireActivity().application)
        val viewModel: MainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        val binding = FragmentMainBinding.inflate(inflater)                                         //binding
        binding.lifecycleOwner = this                                                               //lifecyleOwner
        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
            //TODO tanti qua usano un metodo viewmodel.displayPropertyDetails(it)
        })
        binding.asteroidRecycler.adapter = adapter

        /**IMAGE*/
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            try {
                Picasso.get().load(it.url).into(binding.activityMainImageOfTheDay)
            } catch (e: Exception) {
            }
        })

        /**ASTEROID LIST*/
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
                adapter.data.clear()
                adapter.data.addAll(it)
                adapter.notifyDataSetChanged()
        })

        setHasOptionsMenu(true)
        return binding.root
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