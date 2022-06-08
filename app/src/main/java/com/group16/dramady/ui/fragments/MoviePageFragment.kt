package com.group16.dramady.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentMoviePageBinding
import com.group16.dramady.ui.adapters.ReviewsListAdapter
import com.group16.dramady.ui.models.MoviePageViewModel
import com.group16.dramady.ui.movie_page.MovieParseable
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MoviePageFragment : Fragment() {
    //Factory to pass args into viewmodel
    protected inline fun <VM: ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T: ViewModel> create(aClass: Class<T>):T = f() as T
        }

    private lateinit var viewModel: MoviePageViewModel

    private var _binding: FragmentMoviePageBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory { MoviePageViewModel(arguments?.getString("id")) }).get(MoviePageViewModel::class.java)
        _binding = FragmentMoviePageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var reviewId: Int = -1
        val titleId = arguments?.getString("id")
        val movie: MovieParseable = arguments?.getSerializable("movie") as MovieParseable

        val favouriteIcon = binding.favouriteMoviePage
        val watchlistIcon = binding.watchlistMoviePage
        val reviewsButton = binding.reviewsButtonMoviePage
        val leaveReviewButton = binding.leaveReviewButton
        val myReviewTitle = binding.myReviewTitleMoviePage
        val myReviewContent = binding.myReviewContentMoviePage
        myReviewContent.isEnabled = false
        myReviewContent.isVisible = false
        myReviewTitle.isEnabled = false
        myReviewTitle.isVisible = false

        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.reviews_dialog)
        val listView = dialog.findViewById<ListView>(R.id.listview_reviews_dialog)
        dialog.setTitle("Reviews:")

        viewModel.myReview.observe(viewLifecycleOwner, Observer {
            reviewId = it.id
            myReviewTitle.isVisible = true
            myReviewTitle.isEnabled = true
            myReviewContent.text = it.review
            myReviewContent.isVisible = true
            myReviewContent.isEnabled = true
        })

        reviewsButton.setOnClickListener {
            dialog.show()
        }

        leaveReviewButton.setOnClickListener {
            val bundle = Bundle()
            if(reviewId != -1){
                bundle.putInt("reviewId", reviewId)
            }
            bundle.putString("titleId", titleId)
            findNavController().navigate(R.id.review, bundle)
        }

        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            if(it != null){
                listView.adapter = ReviewsListAdapter(requireContext(), it)
            }
        })

        viewModel.isFavourited.observe(viewLifecycleOwner, Observer {
            if(it == true){
                favouriteIcon.setImageResource(R.drawable.thumb_up)
                if(titleId != null){
                    favouriteIcon.setOnClickListener {
                        viewModel.deleteFavourite(titleId)
                    }
                }
            } else {
                favouriteIcon.setImageResource(R.drawable.empty_thumb_up)
                if(titleId != null){
                    favouriteIcon.setOnClickListener {
                        viewModel.addFavourite(titleId, movie)
                    }
                }
            }
        })

        viewModel.isWatchlisted.observe(viewLifecycleOwner, Observer {
            if(it == true){
                watchlistIcon.setImageResource(R.drawable.watch_later)
                if(titleId != null){
                    watchlistIcon.setOnClickListener {
                        viewModel.deleteWatchlistItem(titleId)
                    }
                }
            } else {
                watchlistIcon.setImageResource(R.drawable.empty_watch_later)
                if(titleId != null) {
                    watchlistIcon.setOnClickListener {
                        viewModel.addWatchlistItem(titleId, movie)
                    }
                }
            }
        })

        if (titleId != null) {
            Picasso.get()
                .load(movie.getImage())
                .placeholder(R.drawable.no_image)
                .resize(450, 450)
                .centerCrop()
                .into(binding.imageMoviePage)
            binding.titleMoviePage.text = movie.getTitle()
            binding.yearMoviePage.text = movie.getYear()
            binding.imdbRatingMoviePage.text = "("+movie.getImDbRating()+")"
            binding.plotMoviePage.text = movie.getPlot()
            binding.releaseDateMoviePage.text = movie.getReleaseDate()
            binding.runtimeStrMoviePage.text = getString(R.string.runtime_list_item)+ ": "+movie.getRuntimeStr()
            binding.directorsMoviePage.text = getString(R.string.directors_list_item)+ ": "+movie.getDirectors()
            binding.starsMoviePage.text = getString(R.string.stars_list_item)+ ": "+movie.getStars()
            binding.genresMoviePage.text = getString(R.string.genres_list_item)+ ": "+movie.getGenres()
            binding.imDbRating2MoviePage.text = getString(R.string.imdb_rating_list_item)+ ": ("+movie.getImDbRating()+")"
            binding.imdbRatingCountMoviePage.text = getString(R.string.imdb_votes_list_item)+": ("+movie.getImDbRatingVotes()+")"
            binding.metacriticMoviePage.text = getString(R.string.metacritic_list_item)+": ("+movie.getMetacriticRating()+"/100)"
        }


        return root
    }

    override fun onResume() {
        viewModel.refreshMyReview()
        super.onResume()
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviePageViewModel::class.java)
    }

}