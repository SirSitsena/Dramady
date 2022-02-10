package com.group16.dramady.ui.favorite_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.group16.dramady.databinding.FragmentAllTimeBestBinding

class FavoriteListFragment : Fragment() {

    private lateinit var favoriteListViewModel: FavoriteListViewModel
    private var _binding: FragmentAllTimeBestBinding? = null

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

        _binding = FragmentAllTimeBestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAllTimeBest
        favoriteListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}