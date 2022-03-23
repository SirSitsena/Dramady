package com.group16.dramady.ui.movie_page

import android.app.Dialog
import android.graphics.Movie
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentMoviePageBinding
import com.group16.dramady.rest.result_type.Reviews
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.FavouritedMovies
import com.group16.dramady.storage.entity.UserReview
import com.group16.dramady.storage.entity.WatchListMovies
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

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
                listView.adapter = ReviewsArrayAdapter(requireContext(), it)
            }
        })

        viewModel.isFavourited.observe(viewLifecycleOwner, Observer {
            if(it == true){
                favouriteIcon.setImageResource(R.drawable.favourite_filled_icon)
                if(titleId != null && movie != null){
                    favouriteIcon.setOnClickListener {
                        viewModel.deleteFavourite(titleId)
                    }
                }
            } else {
                favouriteIcon.setImageResource(R.drawable.favourite_icon)
                if(titleId != null && movie != null){
                    favouriteIcon.setOnClickListener {
                        viewModel.addFavourite(titleId, movie)
                    }
                }
            }
        })

        viewModel.isWatchlisted.observe(viewLifecycleOwner, Observer {
            if(it == true){
                watchlistIcon.setImageResource(R.drawable.star_filled_icon)
                if(titleId != null && movie != null){
                    watchlistIcon.setOnClickListener {
                        viewModel.deleteWatchlistItem(titleId)
                    }
                }
            } else {
                watchlistIcon.setImageResource(R.drawable.star_icon)
                if(titleId != null && movie != null) {
                    watchlistIcon.setOnClickListener {
                        viewModel.addWatchlistItem(titleId, movie)
                    }
                }
            }
        })

        val imageView = binding.imageMoviePage
        val titleView = binding.titleMoviePage
        val yearView = binding.yearMoviePage
        val imDbRatingView = binding.imdbRatingMoviePage
        val plotView = binding.plotMoviePage
        val releaseDateView = binding.releaseDateMoviePage
        val runtimeStrView = binding.runtimeStrMoviePage
        val directorsView = binding.directorsMoviePage
        val starsView = binding.starsMoviePage
        val genresView = binding.genresMoviePage
        val imDbRatingSecondView = binding.imDbRating2MoviePage
        val imDbRatingCountView = binding.imdbRatingCountMoviePage
        val metacriticView = binding.metacriticMoviePage


        if (titleId != null) {
            val picasso = Picasso.get()
                .load(movie.getImage())
                .resize(450, 450)
                .centerCrop()
                .into(imageView)
            titleView.text = movie.getTitle()
            yearView.text = movie.getYear()
            imDbRatingView.text = "("+movie.getImDbRating().toString()+")"
            releaseDateView.text = movie.getReleaseDate()
            plotView.text = movie.getPlot()
            runtimeStrView.text = "Runtime: "+movie.getRuntimeStr()
            directorsView.text = "Directors: "+movie.getDirectors()
            starsView.text = "Stars: "+movie.getStars()
            genresView.text = "Genres: "+movie.getGenres()
            imDbRatingSecondView.text = "IMDB rating: ("+movie.getImDbRating()+")"
            imDbRatingCountView.text = "Vote count: ("+movie.getImDbRatingVotes()+")"
            metacriticView.text = "Metacritic: ("+movie.getMetacriticRating()+"/100)"
        }


        return root
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviePageViewModel::class.java)

    // TODO: Use the ViewModel
    }

}