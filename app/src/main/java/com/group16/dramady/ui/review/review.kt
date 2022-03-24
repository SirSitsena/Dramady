package com.group16.dramady.ui.review

import android.graphics.Movie
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentMoviePageBinding
import com.group16.dramady.databinding.FragmentReviewBinding
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.UserReview
import kotlinx.coroutines.*

class review : Fragment() {

    companion object {
        fun newInstance() = review()
    }

    private lateinit var viewModel: ReviewViewModel
    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val reviewId = arguments?.getInt("reviewId")
        val titleId = arguments?.getString("titleId")

        val submitButton = binding.submitButtonReview
        val reviewEdittext = binding.editTextReview
        val titleText = binding.titleReview

        if(reviewId != null && reviewId != 0){
            uiScope.launch(Dispatchers.IO) {
                val review = MovieRoomDatabase.getUserReviewDao()?.getReviewById(reviewId)
                if (review != null) {
                    titleText.text = getString(R.string.updateReview)
                    reviewEdittext.setText(review.review)
                    submitButton.setOnClickListener {
                        uiScope.launch(Dispatchers.IO){
                            MovieRoomDatabase.getUserReviewDao()
                                ?.updateReview(reviewEdittext.text.toString(), reviewId)
                        }
                        findNavController().navigateUp()
                    }
                }
            }
        } else {
            if(titleId != null){
                titleText.text = getString(R.string.createReview)
                submitButton.setOnClickListener {
                    val content = reviewEdittext.text.toString()
                    uiScope.launch(Dispatchers.IO){
                        MovieRoomDatabase.getUserReviewDao()?.insert(UserReview(id=0,movieId = titleId, review = content))
                        withContext(Dispatchers.Main){
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
    }

}