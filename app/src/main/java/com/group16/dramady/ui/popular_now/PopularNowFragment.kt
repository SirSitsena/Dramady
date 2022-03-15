package com.group16.dramady.ui.popular_now

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentPopularNowBinding
import com.group16.dramady.ui.movie_page.MoviePageFragment

class PopularNowFragment : Fragment() {

    private lateinit var popularNowViewModel: PopularNowViewModel
    private var _binding: FragmentPopularNowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popularNowViewModel =
            ViewModelProvider(this).get(PopularNowViewModel::class.java)

        _binding = FragmentPopularNowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.listPopularNow
        popularNowViewModel.list.observe(viewLifecycleOwner, Observer {
            listView.adapter = PopularListAdapter(requireActivity(), it)
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}