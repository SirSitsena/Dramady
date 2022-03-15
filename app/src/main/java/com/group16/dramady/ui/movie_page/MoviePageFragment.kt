package com.group16.dramady.ui.movie_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentMoviePageBinding
import com.group16.dramady.storage.MovieRoomDatabase
import com.squareup.picasso.Picasso

class MoviePageFragment : Fragment() {
    private lateinit var viewModel: MoviePageViewModel

    private var _binding: FragmentMoviePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviePageBinding.inflate(inflater, container, false)
        val root: View = binding.root

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


        val titleId = arguments?.getString("id")
        //val movie = arguments?.getSerializable("movie")
        val movie: MovieParseable = arguments?.getSerializable("movie") as MovieParseable
        if (titleId != null) {
            Log.i("title id from args : ", titleId)
            Log.i("movie is : ", movie.getTitle())
            val picasso = Picasso.get()
                .load(movie.getImage())
                .resize(450, 450)
                .centerCrop()
                .into(imageView)
            //imDbRatingCountView.text = movie.getImDbRatingVotes().toString()
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
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviePageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}