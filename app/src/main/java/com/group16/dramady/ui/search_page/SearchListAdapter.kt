package com.group16.dramady.ui.search_page

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.group16.dramady.R
import com.group16.dramady.rest.result_type.SearchMovie
import com.squareup.picasso.Picasso

class SearchListAdapter(private val context: Context,
                        private val dataSource: List<SearchMovie.aMovie>
) : BaseAdapter() {

    private val inflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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

        val titleTextView = rowView.findViewById(R.id.title_list_item) as TextView
        val descTextView = rowView.findViewById(R.id.description_list_item) as TextView
        val imageView = rowView.findViewById(R.id.image_list_item) as ImageView



        val movie = getItem(position)
        titleTextView.text = movie?.title ?: "No title available" //ADD  LOCALIZATION STRINGS
        descTextView.text = movie?.description ?: "No desc available"
        val picasso = Picasso.get()
            .load(movie.image)
            .resize(100, 100)
            .centerCrop()
            .into(imageView)
        Log.i("image string: ", movie?.image.toString())


        return rowView
    }
}