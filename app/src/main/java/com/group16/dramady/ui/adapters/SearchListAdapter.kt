package com.group16.dramady.ui.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.group16.dramady.R
import com.group16.dramady.rest.apiManager
import com.group16.dramady.rest.result_type.SearchMovie
import com.group16.dramady.ui.movie_page.MovieParseable
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class SearchListAdapter(private val context: Context,
                        private val dataSource: List<SearchMovie.aMovie>
) : BaseAdapter() {

    private val inflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun getCount(): Int {
        Log.i("size of movies: ", dataSource.size.toString())
        return dataSource.size
    }

    override fun getItem(position: Int): SearchMovie.aMovie {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflator.inflate(R.layout.list_item_search_result, parent, false)

        val titleTextView = rowView.findViewById(R.id.title_search_list_item) as TextView
        //val descTextView = rowView.findViewById(R.id.description_search_list_item) as TextView
        val imageView = rowView.findViewById(R.id.image_search_list_item) as ImageView



        val rowMovie = getItem(position)
        titleTextView.text = rowMovie?.title ?: "No title available" //ADD  LOCALIZATION STRINGS
        //descTextView.text = movie?.description ?: "No desc available"


        val picasso = Picasso.get()
            .load(rowMovie.image)
            .resize(300, 300)
            .centerCrop()
            .into(imageView)
        //Log.i("image string: ", rowMovie?.image.toString())

        rowView.setOnClickListener {
            uiScope.launch(Dispatchers.IO){
                val movie = apiManager.getMovieByTitleId(rowMovie?.id)
                Log.i("movie: ", movie.toString())
                withContext(Dispatchers.Main) {
                    val parseableMovie = MovieParseable(0,
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
                    val bundle = Bundle()
                    bundle.putString("id", movie?.id)
                    bundle.putSerializable("movie", parseableMovie)
                    rowView.findNavController().navigate(R.id.moviePageFragment, bundle)
                }
            }


        }

        return rowView
    }
}