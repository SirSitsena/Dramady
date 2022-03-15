package com.group16.dramady.ui.all_time_best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.group16.dramady.databinding.FragmentAllTimeBestBinding
import android.os.AsyncTask
import android.widget.ListView
import com.group16.dramady.ui.popular_now.PopularListAdapter
import kotlin.concurrent.thread


class AllTimeBestFragment : Fragment() {

    private lateinit var allTimeBestViewModel: AllTimeBestViewModel
    private var _binding: FragmentAllTimeBestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thread{
            //Todo
        }
        allTimeBestViewModel =
            ViewModelProvider(this).get(AllTimeBestViewModel::class.java)

        _binding = FragmentAllTimeBestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.listAllTimeBest
        allTimeBestViewModel.list.observe(viewLifecycleOwner, Observer {
            listView.adapter = AllTimeBestListAdapter(requireActivity(), it)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}