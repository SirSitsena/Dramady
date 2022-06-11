package com.group16.dramady.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.group16.dramady.R
import com.group16.dramady.rest.apiManager
import com.group16.dramady.rest.result_type.FoundMovies
import com.group16.dramady.ui.movie_page.MovieParseable
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class SearchListAdapter(
    private val context: Context,
    private val dataSource: List<FoundMovies.Movie>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun getCount(): Int {
        Log.i("size of movies: ", dataSource.size.toString())
        return dataSource.size
    }

    override fun getItem(position: Int): FoundMovies.Movie {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: inflater.inflate(R.layout.list_item_search_result, parent, false)

        val titleTextView = itemView.findViewById(R.id.title_search_list_item) as TextView
        val imageView = itemView.findViewById(R.id.image_search_list_item) as ImageView


        val itemMovie = getItem(position)
        titleTextView.text = itemMovie.title


        Picasso.get()
            .load(itemMovie.image)
            .placeholder(R.drawable.no_image)
            .resize(300, 300)
            .centerCrop()
            .into(imageView)

        itemView.setOnClickListener {
            if(apiManager.isOnline(context)){
                uiScope.launch(Dispatchers.IO) {
                    val movie = apiManager.getMovieByTitleId(itemMovie.id)
                    Log.i("movie: ", movie.toString())
                    withContext(Dispatchers.Main) {
                        val parseableMovie = MovieParseable(
                            0,
                            movie?.title,
                            movie?.fullTitle,
                            movie?.year,
                            movie?.image,
                            movie?.releaseDate,
                            movie?.runtimeMins,
                            movie?.runtimeStr,
                            movie?.plot,
                            movie?.directors,
                            movie?.writers,
                            movie?.stars,
                            movie?.genres,
                            movie?.companies,
                            movie?.contentRating,
                            movie?.imDbRating,
                            movie?.imDbRatingCount,
                            movie?.metaCriticRating
                        )
                        val bundle = bundleOf(MovieParseable.KEY_ID to movie?.id, MovieParseable.KEY_MOVIE to parseableMovie )
                        itemView.findNavController().navigate(R.id.moviePageFragment, bundle)
                    }
                }
            } else {
                AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.internet_alert))
                    .setMessage(context.getString(R.string.internet_alert_desc))
                    .setPositiveButton(
                        context.getString(R.string.internet_button)
                    ) { _, _ ->
                    }.show()
            }
        }

        return itemView
    }
}