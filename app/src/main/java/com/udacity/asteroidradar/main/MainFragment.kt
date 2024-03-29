package com.udacity.asteroidradar.main


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = FragmentMainBinding.inflate(inflater)                                         //binding
        binding.lifecycleOwner = this                                                               //lifecyleOwner
        binding.viewModel = viewModel                                                               //viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
        binding.asteroidRecycler.adapter = adapter

        /**IMAGE*/
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            try {
                Picasso.get()
                    .load(it.url)
                    .placeholder(R.drawable.placeholder_picture_of_day)
                    .into(binding.activityMainImageOfTheDay)
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

    //depending on the item selected in the menu, update viewmodel.optionMenu value
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.refreshOptionMenu(item)
        return true
    }
}