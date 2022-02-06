package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //tell the RecyclerView about the adapter to use, while setting up the action
        // to be triggered when the item in the adapter (or better, in the VH) is clicked,
        //passing the data from the item as a safearg
        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.getAsteroidList() //calls the method for getting asteroid list from NASA API
        //Observe the list of asteroids and set it with the API response
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            adapter.data.clear()
            adapter.data.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getImageOfTheDay() //call the method for gettin the pic from NASA API
        //Observe the image and put the API response into the ImageView
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.url).into(binding.activityMainImageOfTheDay)
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
        return true
    }


}




