package com.group16.dramady.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.group16.dramady.R
import com.group16.dramady.storage.entity.WatchListMovie
import com.group16.dramady.ui.movie_page.MovieParseable
import com.squareup.picasso.Picasso

class WatchListAdapter(private val context: Context, private val dataSource: List<WatchListMovie>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): WatchListMovie {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getTitleId(position: Int): String {
        val mov = dataSource[position]
        return mov.id
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: inflater.inflate(R.layout.list_item_cardview, parent, false)

        val titleTextView = itemView.findViewById(R.id.title_card_list_item) as TextView
        val imageView = itemView.findViewById(R.id.image_card_list_item) as ImageView
        val yearTextView = itemView.findViewById(R.id.year_card_list_item) as TextView
        val imdbRatingTextView = itemView.findViewById(R.id.imdb_rating_card_list_item) as TextView
        val genresTextView = itemView.findViewById(R.id.genres_card_list_item) as TextView
        val starsTextView = itemView.findViewById(R.id.stars_card_list_item) as TextView

        val movie = getItem(position)
        titleTextView.text = movie.title //ADD  LOCALIZATION STRINGS
        yearTextView.text = movie.year
        imdbRatingTextView.text = "(" + movie.imDbRating.toString() + "/10)"
        genresTextView.text = movie.genres
        starsTextView.text = movie.stars

        Picasso.get()
            .load(movie.image)
            .placeholder(R.drawable.no_image)
            .resize(300, 300)
            .centerCrop()
            .into(imageView)

        itemView.setOnClickListener {
            val parseableMovie = MovieParseable(
                movie.rank,
                movie.title,
                movie.fullTitle,
                movie.year,
                movie.image,
                movie.releaseDate,
                movie.runtimeMins,
                movie.runtimeStr,
                movie.plot,
                movie.directors,
                movie.writers,
                movie.stars,
                movie.genres,
                movie.companies,
                movie.contentRating,
                movie.imDbRating.toString(),
                movie.imDbRatingVotes.toString(),
                movie.metacriticRating
            )
            val bundle = bundleOf(MovieParseable.KEY_ID to movie.id, MovieParseable.KEY_MOVIE to parseableMovie )
            itemView.findNavController().navigate(R.id.moviePageFragment, bundle)
        }

        return itemView
    }
}