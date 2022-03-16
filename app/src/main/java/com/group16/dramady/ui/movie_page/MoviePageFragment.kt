package com.group16.dramady.ui.movie_page

import android.graphics.Movie
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.group16.dramady.R
import com.group16.dramady.databinding.FragmentMoviePageBinding
import com.group16.dramady.storage.MovieRoomDatabase
import com.group16.dramady.storage.entity.FavouritedMovies
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




        val titleId = arguments?.getString("id")
        val movie: MovieParseable = arguments?.getSerializable("movie") as MovieParseable

        val favouriteIcon = binding.favouriteMoviePage
        val watchlistIcon = binding.watchlistMoviePage

        viewModel.isFavourited.observe(viewLifecycleOwner, Observer {
            if(it == true){
                favouriteIcon.setImageResource(R.drawable.favourite_filled_icon)
                if(titleId != null && movie != null){
                    favouriteIcon.setOnClickListener {
                        uiScope.launch(Dispatchers.IO){
                            Log.i("Removing from favourites: ", movie.toString())
                            MovieRoomDatabase.getFavouritedMoviesDao()?.deleteById(titleId)
                            viewModel.refresh()
                        }
                    }
                }
            } else {
                favouriteIcon.setImageResource(R.drawable.favourite_icon)
                if(titleId != null && movie != null){
                    favouriteIcon.setOnClickListener {
                        uiScope.launch(Dispatchers.IO){
                            // Remove from favourites
                            MovieRoomDatabase.getFavouritedMoviesDao()?.insert(FavouritedMovies(titleId, movie.getRank(), movie.getTitle(), movie.getFullTitle(), movie.getYear(), movie.getImage(), movie.getReleaseDate(), movie.getRuntimeMins(), movie.getRuntimeStr(), movie.getPlot(),
                                movie.getDirectors(), movie.getWriters(), movie.getStars(), movie.getGenres(), movie.getCompanies(), movie.getContentRating(), movie.getImDbRating(), movie.getImDbRatingVotes(), movie.getMetacriticRating()))
                            // Refresh the livedata to reflect the icon on screen.
                            viewModel.refresh()
                        }
                    }
                }
            }
        })

        viewModel.isWatchlisted.observe(viewLifecycleOwner, Observer {
            Log.i("watchlisted: ", it.toString())
            if(it == true){
                watchlistIcon.setImageResource(R.drawable.star_filled_icon)
                if(titleId != null && movie != null){
                    watchlistIcon.setOnClickListener {
                        Log.i("clicked ", "watchlist")
                        uiScope.launch(Dispatchers.IO){
                            MovieRoomDatabase.getWatchlistedMoviesDao()?.deleteById(titleId)
                            viewModel.refresh()
                        }
                    }
                }
            } else {
                watchlistIcon.setImageResource(R.drawable.star_icon)
                if(titleId != null && movie != null) {
                    watchlistIcon.setOnClickListener {
                        Log.i("clicked ", "watchlist")
                        uiScope.launch(Dispatchers.IO){
                            MovieRoomDatabase.getWatchlistedMoviesDao()?.insert(WatchListMovies(titleId, movie.getRank(), movie.getTitle(), movie.getFullTitle(), movie.getYear(), movie.getImage(), movie.getReleaseDate(), movie.getRuntimeMins(), movie.getRuntimeStr(), movie.getPlot(),
                                movie.getDirectors(), movie.getWriters(), movie.getStars(), movie.getGenres(), movie.getCompanies(), movie.getContentRating(), movie.getImDbRating(), movie.getImDbRatingVotes(), movie.getMetacriticRating()))
                            viewModel.refresh()
                        }
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