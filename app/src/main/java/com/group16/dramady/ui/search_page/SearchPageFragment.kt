package com.group16.dramady.ui.search_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.group16.dramady.databinding.FragmentSearchPageBinding
import com.group16.dramady.rest.ImdbManager
import com.group16.dramady.rest.result_type.SearchMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class SearchPageFragment : Fragment() {

    private lateinit var searchPageViewModel: SearchPageViewModel
    private var _binding: FragmentSearchPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchPageViewModel =
            ViewModelProvider(this).get(SearchPageViewModel::class.java)

        _binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSearchPage
        searchPageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val resultsList: ListView = binding.searchResultsSearchPage
        resultsList.adapter =
            searchPageViewModel.resultList.value?.let { SearchListAdapter(requireActivity(), it) }
        searchPageViewModel.resultList.observe(viewLifecycleOwner, Observer {
            resultsList.adapter = SearchListAdapter(requireActivity(), it)
        })

        val loadingText: TextView = binding.searchTextSearchPage
        searchPageViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            loadingText.text = it
        })

        val editText: EditText = binding.searchKeywordsSearchPage

        val searchButton: Button = binding.searchButtonSearchPage

        searchButton.setOnClickListener {
            val keywords = editText.editableText.toString()
            searchPageViewModel.search(keywords)
        }

//        thread { //Testing the thread and OkHttp library
//            ImdbManager.getPopularNowList()
//        }


            return root
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}