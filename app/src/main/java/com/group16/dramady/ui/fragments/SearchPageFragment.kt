package com.group16.dramady.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.group16.dramady.MainActivity
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentSearchPageBinding
import com.group16.dramady.rest.apiManager
import com.group16.dramady.ui.adapters.SearchListAdapter
import com.group16.dramady.ui.models.ResultErrors
import com.group16.dramady.ui.models.SearchPageViewModel

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

        val resultsList: ListView = binding.searchResultsSearchPage
        resultsList.adapter =
            searchPageViewModel.resultList.value?.let { SearchListAdapter(requireActivity(), it) }

        searchPageViewModel.resultList.observe(viewLifecycleOwner) {
            resultsList.adapter = SearchListAdapter(requireActivity(), it)
        }

        val loadingBar = binding.progressBar
        searchPageViewModel.searchStatus.observe(viewLifecycleOwner) {
            if (it) {
                loadingBar.visibility = View.VISIBLE
            } else {
                loadingBar.visibility = View.INVISIBLE
            }
        }

        val errorResultText = binding.searchTextSearchPage
        searchPageViewModel.resultError.observe(viewLifecycleOwner) {
            if(it == ResultErrors.NONE) {
                errorResultText.visibility = View.INVISIBLE

            } else if(it == ResultErrors.EMPTY_RESULTS){
                errorResultText.text = getString(R.string.empty_search_result)
                errorResultText.visibility = View.VISIBLE
            }
        }

        val editText: EditText = binding.searchKeywordsSearchPage
        val searchButton: Button = binding.searchButtonSearchPage

        searchButton.setOnClickListener {
            hideKeyboard()
            val phrase = editText.editableText.toString()
            if (phrase.length < 3) {
                Toast.makeText(
                    requireActivity(), R.string.toast_search_length,
                    Toast.LENGTH_LONG
                ).show()

            } else {
                binding.progressBar.visibility = View.VISIBLE
                searchPageViewModel.search(phrase) {
                    if(apiManager.isOnline(requireContext())){
                        Toast.makeText(
                            requireContext(), R.string.search_server_fail,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(), R.string.search_page_fetch_fail,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}