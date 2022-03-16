package com.group16.dramady.ui.favorite_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.group16.dramady.databinding.FragmentFavoriteListBinding
import com.group16.dramady.ui.watch_list.WatchListAdapter

class FavoriteListFragment : Fragment() {

    private lateinit var favoriteListViewModel: FavoriteListViewModel
    private var _binding: FragmentFavoriteListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteListViewModel =
            ViewModelProvider(this).get(FavoriteListViewModel::class.java)

        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFavoriteList
        favoriteListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val listView = binding.listViewFavouritePage
        favoriteListViewModel.list.observe(viewLifecycleOwner, Observer {
            listView.adapter = FavouriteListAdapter(requireActivity(), it)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}