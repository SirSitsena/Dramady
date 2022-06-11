package com.group16.dramady.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentMoviesListBinding
import com.group16.dramady.storage.entity.AllTimeBestMovie
import com.group16.dramady.storage.entity.FavoritedMovie
import com.group16.dramady.storage.entity.PopularNowMovie
import com.group16.dramady.storage.entity.WatchListMovie
import com.group16.dramady.ui.adapters.AllTimeBestListAdapter
import com.group16.dramady.ui.adapters.FavoriteListAdapter
import com.group16.dramady.ui.adapters.PopularListAdapter
import com.group16.dramady.ui.adapters.WatchListAdapter
import com.group16.dramady.ui.models.*


class MoviesListFragment : Fragment() {
    private var viewModel: MoviesListI<*>? = null
    private var _binding: FragmentMoviesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val destinationId = findNavController().currentDestination?.id

        viewModel = when (destinationId) {
            R.id.popular_now -> ViewModelProvider(this).get(PopularNowViewModel::class.java)
            R.id.all_time_best -> ViewModelProvider(this).get(AllTimeBestViewModel::class.java)
            R.id.favorite_list -> ViewModelProvider(this).get(FavoriteListViewModel::class.java)
            R.id.watch_list -> ViewModelProvider(this).get(WatchListViewModel::class.java)

            else -> null
        }

        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        when (destinationId) {
            R.id.popular_now ->
                binding.emptyListText.text = requireActivity().getString(R.string.message_check_internet)
            R.id.all_time_best ->
                binding.emptyListText.text = requireActivity().getString(R.string.message_check_internet)
        }

        val listView: ListView = binding.listMoviesList
        listView.emptyView = binding.emptyListText
        viewModel?.list?.observe(viewLifecycleOwner) {
            listView.adapter = when (destinationId) {
                R.id.all_time_best -> AllTimeBestListAdapter(requireActivity(), it as List<AllTimeBestMovie>)
                R.id.popular_now -> PopularListAdapter(requireActivity(), it as List<PopularNowMovie>)
                R.id.favorite_list -> FavoriteListAdapter(requireActivity(), it as List<FavoritedMovie>)
                R.id.watch_list -> WatchListAdapter(requireActivity(), it as List<WatchListMovie>)
                else -> null
            }
        }
        return root
    }

    override fun onResume() {
        Log.i("UPDATED", "UPDATED")
        super.onResume()
        viewModel?.update()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}