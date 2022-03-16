package com.group16.dramady.ui.watch_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.group16.dramady.databinding.FragmentWatchListBinding
import com.group16.dramady.ui.popular_now.PopularListAdapter

class WatchListFragment : Fragment() {

    private lateinit var watchListViewModel: WatchListViewModel
    private var _binding: FragmentWatchListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        watchListViewModel =
            ViewModelProvider(this).get(WatchListViewModel::class.java)

        _binding = FragmentWatchListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textWatchList
        watchListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val listView = binding.listViewWatchlistPage
        watchListViewModel.list.observe(viewLifecycleOwner, Observer {
            listView.adapter = WatchListAdapter(requireActivity(), it)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}