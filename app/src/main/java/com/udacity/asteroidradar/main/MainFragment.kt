package com.udacity.asteroidradar.main

<<<<<<< HEAD
import android.app.Application
=======
import android.content.Context
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

<<<<<<< HEAD
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var asteroidAdapter: AsteroidAdapter

=======
    //viewModel declaration (extended from MainViewModel.kt class
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

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
<<<<<<< HEAD
        viewModel.asteroids.observe(viewLifecycleOwner) {
            viewModel.getAsteroidList()
=======

        viewModel.apiAsteroidList() //calls the method for getting data from NASA API

        //Observe the API response and set the list accordingly
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            adapter.data.clear()
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)
            adapter.data.addAll(it)
        }

<<<<<<< HEAD
        setHasOptionsMenu(true)
        return binding.root
    }


=======
>>>>>>> parent of bf0a2f7 (Don't know why, but it fuckin works now)


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




