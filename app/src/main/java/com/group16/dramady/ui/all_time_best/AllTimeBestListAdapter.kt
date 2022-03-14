package com.group16.dramady.ui.all_time_best

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.group16.dramady.R
import com.group16.dramady.storage.entity.AllTimeBestMovies
import com.squareup.picasso.Picasso

class AllTimeBestListAdapter(private val context: Context, private val dataSource: List<AllTimeBestMovies>) : BaseAdapter() {
    private val inflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): AllTimeBestMovies {
        return dataSource.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflator.inflate(R.layout.list_item_all_time_best, parent, false)

        val titleTextView = rowView.findViewById(R.id.title_best_list_item) as TextView
        val imageView = rowView.findViewById(R.id.image_best_list_item) as ImageView
        val rankTextView = rowView.findViewById(R.id.rank_best_list_item) as TextView
        val yearTextView = rowView.findViewById(R.id.year_best_list_item) as TextView
        val imdbRatingTextView = rowView.findViewById(R.id.imdb_rating_best_list_item) as TextView
        val genresTextView = rowView.findViewById(R.id.genres_best_list_item) as TextView
        val starsTextView = rowView.findViewById(R.id.stars_best_list_item) as TextView



        val movie = getItem(position)
        titleTextView.text = movie?.title ?: "No title available" //ADD  LOCALIZATION STRINGS
        rankTextView.text = movie?.rank.toString() ?: "N/A"
        yearTextView.text = movie?.year
        imdbRatingTextView.text = "Imdb ("+ movie?.imDbRating.toString()+"/10)" ?: "Imdb (N/A)"
        rankTextView.text = movie?.rank.toString() ?: "N/A"
        genresTextView.text = movie?.genres
        starsTextView.text = movie?.stars
        val picasso = Picasso.get()
            .load(movie.image)
            .resize(150, 150)
            .centerCrop()
            .into(imageView)


        return rowView
    }
}