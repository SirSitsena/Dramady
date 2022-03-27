package com.group16.dramady.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.group16.dramady.R
import com.group16.dramady.storage.entity.WatchListMovies
import com.group16.dramady.ui.movie_page.MovieParseable
import com.squareup.picasso.Picasso

class WatchListAdapter(private val context: Context, private val dataSource: List<WatchListMovies>) : BaseAdapter() {
    private val inflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): WatchListMovies {
        return dataSource.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getTitleId(position: Int): String {
        val mov = dataSource.get(position)
        return mov.id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflator.inflate(R.layout.list_item_cardview, parent, false)

        val titleTextView = rowView.findViewById(R.id.title_card_list_item) as TextView
        val imageView = rowView.findViewById(R.id.image_card_list_item) as ImageView
        val yearTextView = rowView.findViewById(R.id.year_card_list_item) as TextView
        val imdbRatingTextView = rowView.findViewById(R.id.imdb_rating_card_list_item) as TextView
        val genresTextView = rowView.findViewById(R.id.genres_card_list_item) as TextView
        val starsTextView = rowView.findViewById(R.id.stars_card_list_item) as TextView

        val movie = getItem(position)
        titleTextView.text = movie?.title ?: "No title available" //ADD  LOCALIZATION STRINGS
        yearTextView.text = movie?.year ?: "Year (N/A)"
        imdbRatingTextView.text = "("+ movie?.imDbRating.toString()+"/10)" ?: "(N/A)"
        genresTextView.text = movie?.genres
        starsTextView.text = movie?.stars

        val picasso = Picasso.get()
            .load(movie.image)
            .resize(300, 300)
            .centerCrop()
            .into(imageView)

        rowView.setOnClickListener {
            val parseableMovie = MovieParseable(movie.rank, movie.title, movie.fullTitle, movie.year, movie.image, movie.releaseDate, movie.runtimeMins, movie.runtimeStr, movie.plot,
                movie.directors, movie.writers, movie.stars, movie.genres, movie.companies, movie.contentRating, movie.imDbRating.toString(), movie.imDbRatingVotes.toString(), movie.metacriticRating)
            val bundle = Bundle()
            bundle.putString("id", movie.id)
            bundle.putSerializable("movie", parseableMovie)
            rowView.findNavController().navigate(R.id.moviePageFragment, bundle)
        }

        return rowView
    }
}