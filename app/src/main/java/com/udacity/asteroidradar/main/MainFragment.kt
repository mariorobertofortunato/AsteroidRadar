package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    //viewModel declaration (extended from MainViewModel.kt class
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //binding init & inflate
        val binding = FragmentMainBinding.inflate(inflater)

        //declare lifecycle
        binding.lifecycleOwner = this

        //viewModel binding
        binding.viewModel = viewModel

        //tells the RecyclerView about the adapter to use, while setting up the action
        // to be triggered when the item in the adapter (or better, in the VH) is clicked,
        //passing the data from the item as a safearg
        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
        binding.asteroidRecycler.adapter = adapter


        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
                //TODO
        })



        viewModel.dummyAsteroid(adapter) //call function to create list of dummy asteroid
        viewModel.apiAsteroidList()


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




