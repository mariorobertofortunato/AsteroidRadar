package com.udacity.asteroidradar.main


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

    val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModelFactory = MainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter = adapter

        viewModel.getImageOfTheDay()
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.url).into(binding.activityMainImageOfTheDay)
        })

        viewModel.getAsteroidList()
        viewModel.allAsteroids.observe(viewLifecycleOwner, Observer {
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








